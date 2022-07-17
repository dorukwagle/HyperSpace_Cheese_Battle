package u.doruk.hyperspace.cheese.battle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.OverlayLayout;

import java.awt.Dimension;
import java.awt.Image;

public class Square {
    //define some instance attributes
    private byte position;
    private String direction;
    private boolean isCheese;

    //store player name so that specific rocket can be drawn
    private String playerName;

    //store if rocket is placed in this square
    private boolean rocketPlaced = false;

    JButton btn;
    ImageIcon cheese;
    ImageIcon arrowDir; //arrow direction
    ImageIcon rocket;   // rocket
    
    public Square(byte position, String direction, boolean isCheese){
        //initialize all the variables
        this.position = position;
        this.direction = direction;
        this.isCheese = isCheese;
        
        //create square button
        btn = new JButton();
        btn.setOpaque(false);
        btn.setEnabled(false);
        btn.setLayout(new OverlayLayout(btn));
        
        //add image arrow to the button
        arrowDir = new ImageIcon( new ImageIcon( Main.RES_PATH + "/" + direction + ".png").getImage()
        .getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        btn.setIcon(arrowDir);

        if(isCheese)
            this.drawCheese();
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

    //return if rocket is placed in this square
    public boolean rocketPlaced(){
        return this.rocketPlaced;
    }

    private void drawCheese(){
        cheese = new ImageIcon(new ImageIcon(Main.RES_PATH + "/cheese.png").getImage()
            .getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        JLabel img = new JLabel(cheese);
        btn.add(img);
    }
    //method for creating rocket
    public void placeRocket(String playerName){
        //initialize player name
        this.playerName = playerName;
        this.rocketPlaced = true;
        
    }

    //method for removing rocket
    public void removeRocket(){
        this.rocketPlaced = false;

    }

}
