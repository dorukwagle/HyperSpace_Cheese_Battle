package u.doruk.hyperspace.cheese.battle;
import java.awt.Toolkit;
import java.awt.Dimension;

public class Main{
    public static final String  RES_PATH = new Main().getClass().getResource("/src").toString().replace("file:", "");
    public Main(){
       
    }
    public static void main(String[] args) throws Exception{
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // new Board((int) screenSize.getWidth() - 50, (int) screenSize.getHeight() - 100, new Main().getClass().getResource("/src").toString());
        new Board(1200, 1000, new Main().getClass().getResource("/src").toString());
    }
    
}