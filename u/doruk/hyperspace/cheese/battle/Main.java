package u.doruk.hyperspace.cheese.battle;

import java.util.Scanner;

public class Main{
    public static final String  RES_PATH = new Main().getClass().getResource("/src").toString().replace("file:", "");
    private static byte numberOfPlayer;
    private static String[] totalPlayers;
    public Main(){
       
    }
    public static byte getPlayerCount(){
        return Main.numberOfPlayer;
    }
    public static String[] getPlayersName(){
        return Main.totalPlayers;
    }

    private static boolean invalidCount(byte n){
        return ( numberOfPlayer <= 1 ) || ( numberOfPlayer > 4);
    }
    public static void main(String[] args) throws Exception{
        // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of players: ");
        numberOfPlayer = scanner.nextByte();
        while(invalidCount(numberOfPlayer)){
            System.out.println("Minimum number of players is 2 and maximum is 4");
            System.out.print("Enter the number of players: ");
            numberOfPlayer = scanner.nextByte();
        }
        //now initialize String[]
        //consume the left over \n from nextByte()
        scanner.nextLine();
        totalPlayers = new String[numberOfPlayer];
        for(int i = 0; i < numberOfPlayer; ++i){
            System.out.printf("Enter the player %d name: ", i + 1);
            totalPlayers[i] = scanner.nextLine();
        }
        scanner.close();
        new Board(1200, 1000);
    }
    
}