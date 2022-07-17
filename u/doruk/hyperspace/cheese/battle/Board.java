package u.doruk.hyperspace.cheese.battle;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

import java.awt.*;
import java.net.URL;


public class Board{
    //define the size of buttons
    private byte btnWidth = 100;
    private byte btnHeight = 30;

    //define which position will have which direction arrow
    private byte[] upArrow = {1, 2, 3, 4, 5, 21, 22, 23, 24, 25, 46, 47, 48, 49, 50, 66, 67, 68, 69, 81, 82, 83, 84, 85, 90};
    private byte[] downArrow = {96, 97, 98, 99, 86, 87, 88, 89, 26, 27, 28, 29, 71, 76, 73, 78, 75, 80, 52, 57, 54, 59, 62, 64, 65};
    private byte[] leftArrow = {6, 7, 8, 9, 10, 60, 20, 30, 40, 70, 19, 39, 79, 77, 74, 38, 72, 12, 14, 17, 32, 34, 42, 44, 100};
    private byte[] rightArrow = {91, 92, 93, 94, 95, 11, 13, 15, 16, 18, 31, 33, 35, 36, 37, 41, 43, 45, 51, 55, 53, 56, 58, 61, 63};

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

    public Board( int width, int height, String resources ){
        //create jframe main application window
        JFrame window = new JFrame("HyperSpace Cheese Battle");
        window.setSize(new Dimension(width, height));
        // window.getContentPane().setLayout(new BorderLayout());

        //add background image
        ImageIcon img = new ImageIcon( new ImageIcon(Main.RES_PATH + "/space.jpg").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT) );
        JLabel bg = new JLabel(img);
        bg.setLayout(new FlowLayout());
        window.add(bg);
        
        //create main vertical layout that will hold all the components
        JPanel mainLay = new JPanel();
        mainLay.setLayout(new BoxLayout(mainLay, BoxLayout.Y_AXIS));
        mainLay.setOpaque(false);
        bg.add(mainLay);

        //create a board layout to hold the board or the game's squares
        JPanel boardLay = new JPanel();
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
            byte pos = (byte) (i + 1); //index from 0
            String dir = this.getDirection(pos);
            boolean cheese = this.checkCheese(pos);
            square[i] = new Square(pos, dir, cheese);
            boardLay.add(square[i].getSquare());
        }
        //#####################################################################################

        //create a text label 
        JLabel info = new JLabel("Control Dashboard!");
        info.setForeground(Color.YELLOW);
        mainLay.add(info);

        //create textview for user interaction
        JTextArea text = new JTextArea();
        text.setPreferredSize(new Dimension(width, 60));
        text.setBackground(Color.DARK_GRAY);
        text.setEditable(false);
        mainLay.add(text);

        //create a horizontal layout for holding buttons
        JPanel btnLay = new JPanel();
        btnLay.setLayout(new BoxLayout(btnLay, BoxLayout.X_AXIS));
        btnLay.setPreferredSize(new DimensionUIResource(width, 40));

        //create buttons
        JButton btnRollDice = new JButton("Roll Dice");
        btnRollDice.setPreferredSize(new Dimension(btnWidth, btnHeight));
        btnRollDice.setEnabled(false);
        btnLay.add(btnRollDice);

        JButton btnMoveRocket = new JButton("Move Rocket");
        btnMoveRocket.setPreferredSize(new Dimension(btnWidth, btnHeight));
        btnMoveRocket.setEnabled(false);
        btnMoveRocket.setOpaque(false);
        btnLay.add(btnMoveRocket);

        JButton btnDestroyEngine = new JButton("Destroy Engine");
        btnDestroyEngine.setPreferredSize(new Dimension(btnWidth, btnHeight));
        btnDestroyEngine.setEnabled(false);
        btnLay.add(btnDestroyEngine);
        mainLay.add(btnLay);

        window.pack();
        window.setVisible(true);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}