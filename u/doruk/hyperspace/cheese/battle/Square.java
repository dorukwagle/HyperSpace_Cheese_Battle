package u.doruk.hyperspace.cheese.battle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.OverlayLayout;
import javax.swing.border.LineBorder;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;

public class Square {
    //define width and height of images and buttons
    private byte imgWidth = 60;
    private byte imgHeight = 50;
    private byte cheeseWidth = 80;
    private byte cheeseHeight = 70;
    private byte btnWidth = 70;
    private byte btnHeight = 85;

    private float imgAlignmentX = 0.5f;

    //define some instance attributes
    private byte position;
    private String direction;
    private boolean isCheese;

    //store if rocket is placed in this square
    private boolean rocketPlaced = false;

    private JButton btn;
    private ImageIcon cheese;
    private ImageIcon arrowDir; //arrow direction
    private ImageIcon rocket;   // rocket

    //store current player when placed in this square
    private Player currentPlayer;

    //store jlabel i.e. player rocket for start button as well as other buttons
    private JLabel currentRocket;

    private static JLabel[] rocketsAtStart = new JLabel[Main.getPlayerCount()];
    
    public Square(byte position, String direction, boolean isCheese){
        //initialize all the variables
        this.position = position;
        this.direction = direction;
        this.isCheese = isCheese;
        
        //create square button
       btn = new JButton();
        // btn = new JButton(String.valueOf(this.position));
        // btn.setOpaque(false);
        btn.setEnabled(false);
        btn.setPreferredSize(new Dimension(btnWidth, btnHeight));
        // btn.setBackground(new Color(50, 128, 128, 128));
        btn.setBackground(new Color(100, 0, 255, 123));
        btn.setBorder(new LineBorder(new Color(192, 192, 192)));
        btn.setFont(new Font("Ariel", Font.BOLD, 45));
        btn.setForeground(Color.DARK_GRAY);
        btn.setLayout(new OverlayLayout(btn));
        
        
        //if it is first ro last square than write start and win else draw arrow
        if(position == 1){
            btn.setText("Start");
            return;
        }
        else if(position  == 100){
            btn.setText("Win");
            return;
        }
        
        //add image arrow to the button
        arrowDir = new ImageIcon( new ImageIcon( Main.RES_PATH + "/" + direction + ".png").getImage()
        .getScaledInstance(imgWidth, imgHeight, Image.SCALE_DEFAULT));
        
        JLabel arrow = new JLabel(arrowDir);
        arrow.setPreferredSize(new Dimension(imgWidth, imgHeight));
        arrow.setAlignmentX(imgAlignmentX);
        //add arrow after the cheese is added
        btn.add(arrow);

        if(isCheese)
            this.drawCheese();
    }
    
    private void drawCheese(){
        cheese = new ImageIcon(new ImageIcon(Main.RES_PATH + "/cheese.png").getImage()
        .getScaledInstance(cheeseWidth, cheeseHeight, Image.SCALE_DEFAULT));
        JLabel img = new JLabel(cheese);
        img.setAlignmentX(imgAlignmentX);
        btn.add(img);
    }
    
    //method for creating rocket
    public void placeRocket(Player player){
        this.currentRocket = player.getPlayer();
        this.btn.add(currentRocket);
        this.rocketPlaced = true;
        this.currentPlayer = player;
        player.setPosition(this.position);
    }
    
    //method for removing rocket, when the rocket moves to next square remove from current square
    public void removeRocket(){
        this.btn.remove(this.currentRocket);
        this.rocketPlaced = false;
        this.currentPlayer = null;
    }
    
    //method for placing all the rocket at start
    public void startRocket(Player player){
        rocketsAtStart[player.getPlayerId() - 1] = player.getPlayer();
        player.setPosition(this.position);
        this.btn.add(rocketsAtStart[player.getPlayerId() - 1]);
    }
    
    //method for removing a single rocket from start position
    public void removeFromStart(Player player){
        this.btn.remove(rocketsAtStart[player.getPlayerId() - 1]);
        this.btn.revalidate();
        this.btn.repaint();
    }
    

    public byte getPosition(){
        return position;
    }
    public String getDirection(){
        return direction;
    }
    public boolean isCheese(){
        return isCheese;
    }
    
    //return the instance of button
    public JButton getSquare(){
        return btn;
    }
    
    //returns if there is already a rocket in this space or not
    public boolean isRocketPlaced(){
        return this.rocketPlaced;
    }
}
