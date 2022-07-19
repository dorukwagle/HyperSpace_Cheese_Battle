package u.doruk.hyperspace.cheese.battle;

import java.util.Random;

public class Dice {
    private byte maxValue = 6;
    private byte minValue = 1;

    public byte rollDice(){
        Random random = new Random();
        //take some time to roll the dice, takes 2 seconds
        long startTime = System.currentTimeMillis();
        while ( (System.currentTimeMillis() - startTime) < 2000 ){}
        byte roll = (byte) (minValue + random.nextInt(maxValue));
        return roll;
    }
}
