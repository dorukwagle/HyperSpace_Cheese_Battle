package u.doruk.hyperspace.cheese.battle;

import javax.swing.JLabel;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Player {
    private byte playerId;
    private String playerName;
    private String playerRocket;
    private byte position = 1;
    //store the player rocket
    private JLabel player;
    //define width and height in other squares except start square
    private byte width = 80;
    private byte height = 90;

    public Player(byte id, String name){
        this.playerId = id;
        this.playerName = name;
        this.playerRocket = Main.RES_PATH + "/rocket" + playerId + ".png";
    }

    public String getPlayerName(){
        return this.playerName;
    }

    public byte getPlayerId(){
        return this.playerId;
    }

    public byte getPosition(){
        return this.position;
    }

    public void setPosition(byte pos){
        this.position = pos;
    }

    //returns the Player object i.e. player rocket image
    public JLabel getPlayer(){
        ImageIcon img = new ImageIcon( new ImageIcon(this.playerRocket).getImage()
                        .getScaledInstance(width, height, Image.SCALE_DEFAULT));
        this.player = new JLabel(img);
        return this.player;
    }
}
