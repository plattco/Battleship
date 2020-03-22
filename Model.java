/********************************************************************************************
We will likely need a second board one for player 1 and one for player 2
Ideas for solving:
1.Make a new board class that creates the board as an object and create the objects in model
They would also need to be given as method parameters to any method altering the board so that
we  are editing the correct board for the correct player
 ********************************************************************************************/

public class Model {
    //Made board 9x9 to avoid index out of bounds ship placing errors
    int [][] board1;
    int [][] board2;

    /**
     * Constructor for the model
     * @param board1 board for player 1
     * @param board2 board for player 2
     */
    public Model(int [][] board1, int [][] board2) {
        this.board1 = board1;
        this.board2 = board2;
    }    
    
    public final int EMPTY_SPACE = 0;
    public final int MISSED_SHOT = 1;
    public final int SHIP_SPACE = 2;
    public final int HIT_SHOT = 3;
    boolean shipSunk;
    boolean isOver = false;
    boolean invalid = false;
/**
 * Changes the value at a position of the board
 * @param positionX x coordinate of position on board
 * @param positionY y coordinate of position on board
 * @return updated board
 */
    public boolean shot(final int positionX, final int positionY, int [][] board) {
        //0 means empty space, 1 means a missed shot, 2 a space with a ship, 3 means a hit shot
        if(board[positionX][positionY] == EMPTY_SPACE) {
            board[positionX][positionY] = MISSED_SHOT;
            return false;
        }
        else if(board[positionX][positionY] == SHIP_SPACE) {
            board[positionX][positionY] = HIT_SHOT;
        }
        return true;
    }

    /**
     * CHecks to see if a given shot is valid
     * @param positionX position on x axis
     * @param positionY position on y axis
     * @param board the current board being shot at
     * @return true is a valid shot false is invalid
     */
    public boolean validShot(final int positionX, final int positionY, int [][] board) {
        //Checking that postion doesn't go out of bound on y axis
        if(positionX < 0 || positionY < 0) {
            return false;
        }
        if(positionX > 7 || positionY > 7) {
            return false;
        } 
        if(board[positionX][positionY] == HIT_SHOT || board[positionX][positionY] == MISSED_SHOT) {
            return false;
        }
        return true;
    }

/**WORKING
 * Checks if any ship spaces are left
 * @return true if there are no ships left, false otherwise
 */
    public boolean gameOver(int [][] board) {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(board[i][j] == SHIP_SPACE) {
                    return false;
                }
            }
        }
        return true;
    }

    /**WORKING
     * Provides logic for a player to place a ship
     * @param positionX x coordinate of position on board
     * @param positionY y coordinate of position on board
     * @param vertical true if the user wants to place the ship vertically false for horizontally
     * @param shipSize length of the ship being placed
     * @return the board updated with a ship placement
     */
    public boolean placeShip(final int positionX, final int positionY, final boolean vertical, int [][] board) {
        //Check for validity of the ship
        invalid = invalidShipErrorCatching(positionX, positionY, vertical, board);
        if(invalid) {
            return false;
        }
        //Does not alter the board if the ship is invalid
        else if(vertical) {
            //Places a vertical ship
            for(int i = 0; i < 3; i++) {
                board[positionX][positionY + i] = SHIP_SPACE;
            }
        } else {
            //Places a horizontal ship
            for(int i = 0; i < 3; i++) {
                board[positionX + i][positionY] = SHIP_SPACE;
            }
        }
        return true;
    }

    /**WORKING
     * Checks if the location chosen by the user is valid for the given ship
     * @param positionX x coordinate on the board
     * @param positionY y coordinate on the board
     * @param vertical true if the ship is being placed vertically false for horizontal
     * @param shipSize length of the ship being placed
     * @param currentPosition position along the ship being checked for validity
     * @return true if the ship is an invalid placement false if valid
     */
    public boolean invalidShipErrorCatching(final int positionX, final int positionY, final boolean vertical, int [][] board) {
        if(positionX < 0 || positionY < 0) {
            return true;
        }
        //Checking that postion doesn't go out of bound on y axis
        if((positionX > 7 || positionY + 2 > 7) && vertical) {
            return true;
        }//Checking that position doesn't go out of bounds on x axis
        else if((positionX + 2 > 7 || positionY > 7) && !vertical) {
            return true;
        }//Checking that ship does not overlap with any other ship on y axis
        else if((board[positionX][positionY] == SHIP_SPACE && vertical) || (board[positionX][positionY + 1] == SHIP_SPACE && vertical) || (board[positionX][positionY + 2] == SHIP_SPACE && vertical)) {
            return true;
        }//Checking that ship does not overlap with any other ship on y axis 
        else if((board[positionX][positionY] == SHIP_SPACE && !vertical) || (board[positionX + 1][positionY] == SHIP_SPACE && !vertical) || (board[positionX + 2][positionY] == SHIP_SPACE && !vertical)) {
            return true;
        }
        return false;
    }
}