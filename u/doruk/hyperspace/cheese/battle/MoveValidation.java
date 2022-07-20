package u.doruk.hyperspace.cheese.battle;

public class MoveValidation {
    //method to check if the move is valid or not
    //it checks the direction of movement and calculates the required square
    //if there is not enough square to make the move than returns 0
    private static byte validateMove(byte diceNum, Player player, Square[] square){
        //lets assume the player cannot make any move
        byte validSquare = 0;

        //first check the current position of player
        byte playerPos = player.getPosition();
        //now get the corresponding square the player is in, it is simply player position - 1 in the square index
        Square playerSquare = square[playerPos - 1];
        String direction = playerSquare.getDirection();
        //check which direction to move and get the squares
        //also check if there are enough square to make move in the given direction
        if(direction.equals("up")){
            validSquare = (byte) (playerPos + diceNum * 10);
            if( validSquare > 100 ) //this move is invalid as there are no enough squares to move
                validSquare = 0;
        }
        else if(direction.equals("down")){
            validSquare = (byte) ( playerPos - diceNum * 10 );
            if( validSquare < 1 )
                validSquare = 0;
        }
        else if( direction.equals("right") ){
            validSquare = (byte) ( playerPos + diceNum );
            if ( ( (int)(validSquare / 10) > (int)(playerPos / 10) ))
                validSquare = 0;
        }
        else if( direction.equals("left") ){
            validSquare = (byte) ( playerPos - diceNum );
            if ( ( (int)(validSquare / 10) < (int)(playerPos / 10) ) )
                validSquare = 0;
        }
        return validSquare;
    }

    //method to check if the valid square is empty or not and increase the position by 1 in the direction of arrow
    //until the empyt square is found
    public static byte getValidSquare(byte diceNum, Player player, Square[] square){
        byte emptySquare = 0;
        emptySquare = validateMove(diceNum, player, square);
        //if empty square is 0 than the move is not allowed
        if(emptySquare == 0)
            return 0;
        if(emptySquare < 1 || emptySquare > 100)
            return 0;

        while (square[emptySquare - 1].isRocketPlaced()){
            //since we always move the rocket 1 square in the direction of valid but occupied square arrow
            //problem : the position of player is previous, i.e. not changed as it is only changed by the place rocket method of square class
            //so we need to explicitely change the player position to that of current valid but occupied square so that it will move 1 square
            player.setPosition(emptySquare); //this emptySquare is not actually empty
            emptySquare = validateMove((byte) 1, player, square);
        }
        return emptySquare;
    }
}
