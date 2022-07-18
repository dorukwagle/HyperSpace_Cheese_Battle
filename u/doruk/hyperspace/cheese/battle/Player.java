package u.doruk.hyperspace.cheese.battle;

import javax.swing.JLabel;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Dimension;

public class Player {
    private byte playerId;
    private String playerName;
    private String playerRocket;
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
    
    //returns the Player object i.e. player rocket image
    public JLabel getPlayer(){
        ImageIcon img = new ImageIcon( new ImageIcon(this.playerRocket).getImage()
                        .getScaledInstance(width, height, Image.SCALE_DEFAULT));
        JLabel player = new JLabel(img);
        return player;
    }
}
