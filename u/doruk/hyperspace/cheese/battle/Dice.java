package u.doruk.hyperspace.cheese.battle;

import java.util.Random;

public class Dice {
    private byte maxValue = 6;
    private byte minValue = 1;

    private static byte[] fixedMatch = {4, 1, 5, 5};
    private static byte count = 0;

    public byte rollDice(){
        if(count < fixedMatch.length){
            return fixedMatch[count++];
        }
        Random random = new Random();
        //take some time to roll the dice, takes 2 seconds
        long startTime = System.currentTimeMillis();
        while ( (System.currentTimeMillis() - startTime) < 700 ){}
        byte roll = (byte) (minValue + random.nextInt(maxValue));
        return roll;
    }
}
