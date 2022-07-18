package u.doruk.hyperspace.cheese.battle;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class UserInteraction implements ActionListener, KeyListener{
    JTextArea text;
    JLabel info;
    JButton btnRollDice;
    JButton btnMoveRocket;
    JButton destroyEngine;

    //initialize Square class
    Square[] square;
    //instantiate player object
    Player[] player;
    String infoLabel;
    String userInput = "";

    public UserInteraction(Square[] square, JLabel info, JTextArea text, JButton btnRollDice,
                            JButton btnMoveRocket, JButton destroyEngine){
        this.btnRollDice = btnRollDice;
        this.btnMoveRocket = btnMoveRocket;
        this.destroyEngine = destroyEngine;
        this.info = info;
        this.square = square;
        this.text = text;
        this.infoLabel = info.getText();
    }

    //display information or action that user needs to perform in info text
    private void setInfoText(String text){
        this.info.setText(this.infoLabel + text);
    }
    //clear anything asked by the game to the user
    private void clearInfoText(){
        this.info.setText(infoLabel);
    }

    //method to ask user something
    private String askUser(String question){
        this.setInfoText(question);
        //now wait until the user enters the player number
         while(this.userInput.equals("")){
            try{
                wait(500);
            }catch(Exception e){}
        }
        String input = this.userInput;
        //make userInput empty again
        this.userInput = "";
        return input;
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
    }

    @Override
    public void actionPerformed(ActionEvent ae){
        // Auto-generated method stub
       
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
            this.userInput = this.text.getText();
            e.consume();
            this.text.setText("");
        }
        if ( ((c < '1') || (c > '6')) && (c != KeyEvent.VK_BACK_SPACE) ) 
            e.consume();  // if it's not a number, ignore the event
    }
     
}
