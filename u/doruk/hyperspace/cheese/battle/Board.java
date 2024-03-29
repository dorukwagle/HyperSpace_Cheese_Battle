package u.doruk.hyperspace.cheese.battle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;


public class Board{
    //define gui variables
    private JFrame window;
    private JPanel boardLay;
    private JPanel mainLay;

    //contents of dashboard
    private JLabel info;
    private JTextArea text;
    private JPanel dashboard;
    private JPanel label;

    //define the size of buttons
    private short btnWidth = 400;
    private byte btnHeight = 60;

    private String infoText = "Control Dashboard: ";
    //define text color for buttons and text area
    Color color = new Color(146, 168, 209);
    //define which position will have which direction arrow
    private byte[] upArrow = {11, 2, 3, 4, 5, 21, 22, 23, 24, 25, 46, 47, 48, 49, 50, 66, 67, 68, 69, 81, 82, 83, 84, 85, 90};
    private byte[] downArrow = {96, 97, 98, 99, 86, 87, 88, 89, 26, 27, 28, 29, 71, 76, 73, 78, 75, 80, 52, 57, 54, 59, 62, 64, 65};
    private byte[] leftArrow = {6, 7, 8, 9, 10, 60, 20, 30, 40, 70, 19, 39, 79, 77, 74, 38, 72, 12, 14, 17, 32, 34, 42, 44, 100};
    private byte[] rightArrow = {91, 92, 93, 94, 95, 1, 13, 15, 16, 18, 31, 33, 35, 36, 37, 41, 43, 45, 51, 55, 53, 56, 58, 61, 63};

    private byte[] cheeseSquare = {23, 37, 49, 67, 75, 80, 91, 52, 62, 96};

   //returns the direction of arrow of square 
    private String getDirection(byte position){
        for( byte b : upArrow )
            if( b == position )
                return "up";
        for( byte b : downArrow )
            if( b == position )
                return "down";
        for( byte b : leftArrow )
            if( b == position )
                return "left";
        for( byte b : rightArrow )
            if( b == position )
                return "right";
        return "";
    }

    //checks whether to draw cheese on the square
    private Boolean checkCheese( byte position ){
        boolean cheese = false;
        for( byte b : cheeseSquare )
            if( b == position )
                cheese = true;
        return cheese;
    }

    //to re paint all the components
    public void repaintBoard(){
        this.boardLay.revalidate();
        this.boardLay.repaint();
    }

    public Board( int width, int height){
        //create jframe main application window
        this.window = new JFrame("HyperSpace Cheese Battle");
        window.setSize(new Dimension(width, height));

        //add background image
        ImageIcon img = new ImageIcon( new ImageIcon(Main.RES_PATH + "/space.jpg").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT) );
        JLabel bg = new JLabel(img);
        bg.setLayout(new FlowLayout());
        window.add(bg);
        
        //create main vertical layout that will hold all the components
        mainLay = new JPanel();
        mainLay.setLayout(new BoxLayout(mainLay, BoxLayout.Y_AXIS));
        mainLay.setOpaque(false);
        bg.add(mainLay);

        //create a board layout to hold the board or the game's squares
        boardLay = new JPanel();
        boardLay.setLayout(new GridLayout(10, 10));
        boardLay.setBounds(0, 0, width, height - 100);
        boardLay.setOpaque(false);
        mainLay.add(boardLay);

        //create squares ##################################################################
        //define the order in which the squares will be added to the board
        byte[] order = {
                91, 92, 93, 94, 95, 96, 97, 98, 99, 100,
                81, 82, 83, 84, 85, 86, 87, 88, 89, 90,
                71, 72, 73, 74, 75, 76, 77, 78, 79, 80,
                61, 62, 63, 64, 65, 66, 67, 68, 69, 70,
                51, 52, 53, 54, 55, 56, 57, 58, 58, 60,
                41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
                31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
                21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10
             };
        Square[] square = new Square[order.length];
        for( int i = 0; i < order.length; i++ ){
            byte pos = order[i]; //index from 0
            String dir = this.getDirection(pos);
            boolean cheese = this.checkCheese(pos);
            square[i] = new Square(pos, dir, cheese);
            boardLay.add(square[i].getSquare());
        }
        //now sort the array of Squares in ascending order based on their position order i.e. square[0] = 91 square[100] = 0
        byte length = (byte) square.length;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (square[i].getPosition() > square[j].getPosition()) {
                    Square temporary = square[i];
                    square[i] = square[j];
                    square[j] = temporary;
                }
            }
        }
        
        //#####################################################################################

        //create a user dashboard
        dashboard = new JPanel();
        dashboard.setLayout(new BoxLayout(dashboard, BoxLayout.Y_AXIS));
        dashboard.setBackground(Color.DARK_GRAY);
        dashboard.setOpaque(true);
        //create a text label 
        label = new JPanel();
        label.setLayout(new  FlowLayout());
        label.setOpaque(false);
        dashboard.add(label);

        info = new JLabel(infoText);
        info.setFont(new Font("Ariel", Font.BOLD, 20));
        info.setHorizontalAlignment(SwingConstants.LEFT);
        info.setPreferredSize(new Dimension(width, 40));
        info.setForeground(Color.YELLOW);
        info.setOpaque(false);
        label.add(info);
        
        //create textview for user interaction
        text = new JTextArea(" Game is starting");
        text.setPreferredSize(new Dimension(width, 35));
        text.setFont(new Font("Ariel", Font.BOLD, 30));
        text.setOpaque(false);
        text.requestFocus();
        text.setForeground(color);
        dashboard.add(text);
        mainLay.add(dashboard);
        
        //create a horizontal layout for holding buttons
        JPanel btnLay = new JPanel();
        btnLay.setLayout(new FlowLayout());
        btnLay.setPreferredSize(new Dimension(width - 100, 60));
        btnLay.setOpaque(true);
        btnLay.setBackground(new Color(192, 0, 255, 123));

        //create buttons
        JButton btnRollDice = new JButton("Roll Dice");
        btnRollDice.setSize(new Dimension(btnWidth, btnHeight));
        btnRollDice.setEnabled(false);
        btnRollDice.setOpaque(true);
        btnRollDice.setBackground(new Color(100, 0, 255, 123));
        btnRollDice.setFont(new Font("Ariel", Font.BOLD, 40));
        btnRollDice.setForeground(color);
        btnLay.add(btnRollDice);

        JButton btnMoveRocket = new JButton("Move Rocket");
        btnMoveRocket.setPreferredSize(new Dimension(btnWidth, btnHeight));
        btnMoveRocket.setEnabled(false);
        btnMoveRocket.setOpaque(true);
        btnMoveRocket.setBackground(new Color(100, 0, 255, 123));
        btnMoveRocket.setFont(new Font("Ariel", Font.BOLD , 40));
        btnMoveRocket.setForeground(color);
        btnLay.add(btnMoveRocket);

        JButton btnDestroyEngine = new JButton("Destroy Engine");
        btnDestroyEngine.setPreferredSize(new Dimension(btnWidth, btnHeight));
        btnDestroyEngine.setEnabled(false);
        btnDestroyEngine.setOpaque(true);
        btnDestroyEngine.setBackground(new Color(100, 0, 255, 123));
        btnDestroyEngine.setFont(new Font("Ariel", Font.BOLD, 40));
        btnDestroyEngine.setForeground(color);
        btnLay.add(btnDestroyEngine);
        mainLay.add(btnLay);

        window.pack();
        window.setVisible(true);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        UserInteraction userInteraction = new UserInteraction(this, square, info, text, btnRollDice, btnMoveRocket, btnDestroyEngine);
        ActionListener actionListener = (ActionListener) userInteraction;
        btnRollDice.addActionListener(actionListener);
        btnMoveRocket.addActionListener(actionListener);
        btnDestroyEngine.addActionListener(actionListener);

        text.addKeyListener((KeyListener) actionListener);
        userInteraction.startGame();

    }
}
