package u.doruk.hyperspace.cheese.battle;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.concurrent.TimeUnit;;


public class UserInteraction implements ActionListener, KeyListener{
    private JTextArea text;
    private JLabel info;
    private JButton btnRollDice;
    private JButton btnMoveRocket;
    private JButton btnDestroyEngine;

    private Board board;

    //initialize Square class
    private Square[] square;
    //instantiate player object
    private Player[] player;
    private String infoLabel;
    
    //declare Dice
    private Dice dice;

    //declare some variables for the program control. i.e. stop or use while loop unless these variables contain certain value
    //store the result of dice
    private byte diceResult = 0;
    private String userInput = "";
    private boolean moveRocketBtnClicked = false;
    private boolean destroyEngineBtnClicked = false;
    //store if player wants to destroy engine or roll  the dice
    String cheeseAction = "";

    public UserInteraction(Board board, Square[] square, JLabel info, JTextArea text, JButton btnRollDice,
                            JButton btnMoveRocket, JButton destroyEngine){
        this.btnRollDice = btnRollDice;
        this.btnMoveRocket = btnMoveRocket;
        this.btnDestroyEngine = destroyEngine;
        this.info = info;
        this.square = square;
        this.text = text;
        this.board = board;
        this.infoLabel = info.getText();
        this.dice = new Dice();
    }
    
    //display information or action that user needs to perform in info text
    private void setInfoText(String text){
        this.info.setText(this.infoLabel + text);
    }

    //method to get the dice number
    private byte getDice(String question){
        this.setInfoText(question);
        while(this.diceResult == 0){
            try{
                TimeUnit.MILLISECONDS.sleep(200);
            }catch(Exception e){}
        }
        byte input = this.diceResult;
        this.diceResult = 0;
        return input;
    }
    //method to ask user something
    private byte askUser(String question){
        this.setInfoText(question);
        //now wait until the user enters the player number
        while(this.userInput.equals("")){
            try{
                TimeUnit.MILLISECONDS.sleep(200);
            }catch(Exception e){}
        }
        byte input = 0;
        try{
            input = Byte.parseByte(this.userInput);
        }catch(Exception e){
            // System.out.println(e.toString());
        }
        //make userInput empty again
        this.userInput = "";
        this.text.setText("");
        return input;
    }

    //play game for each player turn by turn
    private void play(){
        //enable roll dice button
        //check winner
        boolean gameWon = false;
        while(!gameWon){
            //roll dice for every player one by one
            for(int i = 0 ; i < this.player.length; ++i){
                //lets assume current player wins the game
                String winner = player[i].getPlayerName();
                //store initial position of player
                byte initialPosition = player[i].getPosition();
                
                String name = player[i].getPlayerName();
                this.btnRollDice.setEnabled(true);
                byte diceNum = this.getDice(name + ", please roll a dice !");
                
                //since the dice is rolled now this player should move the rocket
                //also make the move dice button clickable
                byte destinationSquare = MoveValidation.getValidSquare(diceNum, player[i], this.square);
                if( destinationSquare == 0 ) { //move is not valid so leave it
                    this.setInfoText(name + ", no " + diceNum + " squares left");
                    try{
                        TimeUnit.MILLISECONDS.sleep(1000);
                    }catch (Exception e){}

                    continue;
                }

                //enable the move rocket button
                this.btnMoveRocket.setEnabled(true);
                this.setInfoText(name + ", got " + diceNum + " please move your rocket!");
                //wait for the move rocket button click
                while(!this.moveRocketBtnClicked){}
                //check if the rocket is at start position and remove it from there
                if(initialPosition == 1){
                    square[initialPosition - 1].removeFromStart(player[i]);
                    this.board.repaintBoard();
                }
                else{
                    //remove rocket from initial place, other than start position
                    square[initialPosition - 1].removeRocket();
                    this.board.repaintBoard();
                }
                //place the player, rocket to the new final position
                square[destinationSquare - 1].placeRocket(player[i]);
                this.moveRocketBtnClicked = false;

                //check if the player won the game
                if(player[i].getPosition() == 100){
                    this.setInfoText(winner + " won the game!!!");
                    this.text.setText("!!!~~~" + winner + "~~~!!! won the game!!!");
                    gameWon = true;
                    this.btnRollDice.setText("Restart Game");
                    this.btnRollDice.setEnabled(true);
                    this.text.setText("Please click 'Restart Game' button to restart the game");
                    break;
                }
                //check if the rocket is at cheese position
                if(!square[destinationSquare - 1].isCheese())
                    continue;
                //if it is cheese than give the player with two optioon
                String question = name + ", consumed cheese. What would you like to do? (1:roll again, 2: destroy engine)";
                byte action = this.askUser(question);
                //if user would like to roll the dice again, than just continue the loop with his turn again
                if(action == 1){
                    --i;
                    continue;
                }
                //if user would like to destroy engine
                question = "Destroy Engine of: ";
                String engineList = "";
                for (int j = 0; j < this.player.length; ++j){
                    if(player[j].getPlayerId() == player[i].getPlayerId())
                        continue;
                    question += (player[j].getPlayerId() + ":" + player[j].getPlayerName());
                    engineList += ("," + String.valueOf(player[j].getPlayerId()));
                }

                byte playerId = this.askUser(question);
                //check if the user enters the valid id
                while(engineList.indexOf(Byte.toString(playerId)) < 0){
                    playerId = this.askUser(question);
                }
                Player play = player[playerId - 1];
                this.setInfoText("Destroy " + play.getPlayerName() + "'s rocket engine");
                this.btnDestroyEngine.setEnabled(true);
                //wait until the button is clicked
                while(!this.destroyEngineBtnClicked){
                    try{
                        TimeUnit.MILLISECONDS.sleep(200);
                    }catch(Exception e){}
                }
                this.destroyEngineBtnClicked = false;
                //since the button is clicked
                //now remove the selected player from its current position and place at start position
                square[play.getPosition() - 1].removeRocket();
                square[0].startRocket(player[playerId - 1]);
                this.board.repaintBoard();
                this.btnDestroyEngine.setEnabled(false);
            }
        }
    }
    public void startGame(){
        //make input area empty
        this.text.setText("");
        //create n number of player instances
        byte playerNumber = Main.getPlayerCount();
        player = new Player[playerNumber];
        //initialize the player class with all the player names
        for(int i = 0; i < playerNumber; ++i){
            player[i] = new Player((byte)(i + 1), Main.getPlayersName()[i]);
        }
        //now start all the players rockets by placing at start position
        for(int i = 0; i < playerNumber; ++i){
            square[0].startRocket(player[i]);
        }
        //now start rolling the dice and play game
        this.play();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae){
        // Auto-generated method stub
        String button = ae.getActionCommand();
        if(button.equals("Roll Dice")){
            this.btnRollDice.setEnabled(false);
            this.diceResult = this.dice.rollDice();
        }
        else if(button.equals("Move Rocket")){
            this.btnMoveRocket.setEnabled(false);
            this.moveRocketBtnClicked = true;
        }
        else if(button.equals("Destroy Engine")){
            this.btnDestroyEngine.setEnabled(false);
            this.destroyEngineBtnClicked = true;
        }
        else if(button.equals("Restart Game")){
            new Board(Main.getBoardWidth(), Main.getBoardHeight());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Auto-generated method stub
        // System.out.println(e.getKeyChar());
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Auto-generated method stub
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Auto-generated method stub
        char c = e.getKeyChar();
        if(c == KeyEvent.VK_ENTER){
            this.userInput = this.text.getText().replaceAll("\n", "");
            e.consume();
            this.text.setText("");
        }
        if ( ((c < '1') || (c > '6')) && (c != KeyEvent.VK_BACK_SPACE) ) 
            e.consume();  // if it's not a number, ignore the event
    }
     
}
