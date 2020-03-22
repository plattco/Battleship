
public class Controller {
    final static int TOTAL_SHIPS = 3;
    
    /**
     * This method runs through both players placing their ships with player 1
     * placing all of their ships then player 2 placing all of theirs
     * @param m the model
     * @param v the view
     */
    public static void placeShips(Model m, View v, boolean isGUI) { 
    	int i = 0;
    	int j = 0;
    	int k = 0;
    	int l = 0;
    	boolean isVertical;
    	int [] coords;

    	if(!isGUI) {
    		while(i <= TOTAL_SHIPS) {
    			v.playerTurn(true);
    			v.updateMyBoard(m.board1, v.myTextBoard, isGUI);
    			if(m.placeShip(v.getX() - 1, v.getY() - 1, v.getOrientation(), m.board1)) {
    				i++;
    			} else {
    				v.invalidShip();
    			}
    		}
    		v.updateMyBoard(m.board1, v.myTextBoard, isGUI);
    		while(j <= TOTAL_SHIPS) {  
    			v.playerTurn(false);
    			v.updateMyBoard(m.board2, v.myTextBoard2, isGUI);
    			if(m.placeShip(v.getX() - 1, v.getY() - 1, v.getOrientation(), m.board2)) {
    				j++;
    			} else {
    				v.invalidShip();
    			}
    		}
    		v.updateMyBoard(m.board2, v.myTextBoard2, isGUI);
    		
    	} else { // Condition for the GUI way
    		while(k <= TOTAL_SHIPS) {
    			coords = v.updateMyBoard(m.board1, v.myTextBoard, isGUI);
    			isVertical = true;
    			if(coords[2] == 1)
    				isVertical = false;
    			if(m.placeShip(coords[0] - 1, coords[1] - 1, isVertical, m.board1)) {
    				System.out.println("help");
    				v.updateMyBoard(m.board1, v.myTextBoard, isGUI);
    				k++;
    			} else {
    				v.invalidShip();
    			}
    		}
    		v.updateMyBoard(m.board2, v.myTextBoard2, isGUI);
    		while(l <= TOTAL_SHIPS) {
    			coords = v.updateMyBoard(m.board2, v.myTextBoard2, isGUI);
    			isVertical = true;
    			if(coords[2] == 1)
    				isVertical = false;
    			if(m.placeShip(coords[0] - 1, coords[1] - 1, isVertical, m.board2)) {
    				v.updateMyBoard(m.board2, v.myTextBoard2, isGUI);
    				l++;
				} else {
					v.invalidShip();
				} 
    		}
    		v.updateMyBoard(m.board2, v.myTextBoard2, isGUI);
    	}
    }
    
    /**
     * This method will hold the game logic. 
     * It will call hold the necessary variables and call the methods to place ships and shoot
     */
    public static void gameLogic() {
        int [][] board1 = new int [11][11];
        int [][] board2 = new int [11][11];
        char [][] myTextBoard = new char[11][11];
        char [][] myTextBoard2 = new char[11][11];
        char [][] oppTextBoard = new char[11][11];
        char [][] oppTextBoard2 = new char[11][11];
        View text = new View(myTextBoard, myTextBoard2, oppTextBoard, oppTextBoard2);
        View gui = new View(myTextBoard, myTextBoard2, oppTextBoard, oppTextBoard2);
        Model m = new Model(board1, board2);
        String view = text.getView();
        boolean turn = true;
        boolean isGUI = false;

        if(view.equals("y")) {
        	isGUI = true;
            gui.gui(gui.myTextBoard, gui.myTextBoard2, gui.oppTextBoard, gui.oppTextBoard2);
            placeShips(m, gui, isGUI);
            shoot(m, gui, turn, isGUI);
        } else {
            placeShips(m, text, isGUI); //I can add a boolean for if this is text vs. GUI ??
            shoot(m, text, turn, isGUI);
        }
        
    }
    
    /**
     * This method will process both players shot and determine a winner once
     * all of one players ships are sunk
     * @param m the model
     * @param v the view
     * @param turn true if player 1's turn false for player 2
     */
    public static void shoot(Model m, View v, boolean turn, boolean isGUI) {
        int x;
        int y;
        int[] coords;
        
        if(!isGUI) {
        	while(!m.gameOver(m.board1) && !m.gameOver(m.board2)) {
        		if(turn) {
        			do {
        				v.playerTurn(true);
        				v.updateOpponentBoard(m.board2, v.oppTextBoard, isGUI);
        				if(m.gameOver(m.board1) || m.gameOver(m.board2)) {
        					break;
        				}
        				x = v.getX() - 1;
        				y = v.getY() - 1;
        				if(!m.validShot(x, y, m.board2)) {
        					v.invalidShot();
        					shoot(m, v, true, isGUI);
        				}
        			} while(m.shot(x, y, m.board2));
        			turn = false;
        		} else {
        			do {
        				v.playerTurn(false);
        				v.updateOpponentBoard(m.board1, v.oppTextBoard2, isGUI);
        				if(m.gameOver(m.board1) || m.gameOver(m.board2)) {
        					break;
        				}
        				x = v.getX() - 1;
        				y = v.getY() - 1;
        				if(!m.validShot(x, y, m.board1)) {
        					v.invalidShot();
        					shoot(m, v, false, isGUI);
        				}
        			} while(m.shot(x, y, m.board1));
        			turn = true;
        		}
        	} System.out.println("Spot 3");
        } else {
        	while(!m.gameOver(m.board1) && !m.gameOver(m.board2)) {
        		if(turn) {
        			System.out.println("Spot 0");
        			do {
        				v.updateOpponentBoard(m.board2, v.oppTextBoard, isGUI);
        				if(m.gameOver(m.board1) || m.gameOver(m.board2)) {
        					break;
        				}
        				coords = v.updateOpponentBoard(m.board2, v.oppTextBoard, isGUI);
        				System.out.println("spot 1");
        				if(!m.validShot(coords[0] - 1 , coords[1] - 1, m.board2)) {
        					v.invalidShot();
        					System.out.println("Spot 2");
        					shoot(m, v, true, isGUI);
        				}
        			} while(m.shot(coords[0] - 1, coords[1] - 1, m.board2));
        			turn = false;
        		} else {
        			do {
        				System.out.println("Spot 4");
        				v.updateOpponentBoard(m.board1, v.oppTextBoard2, isGUI);
        				if(m.gameOver(m.board1) || m.gameOver(m.board2)) {
        					break;
        				}
        				coords = v.updateOpponentBoard(m.board1, v.oppTextBoard2, isGUI);
        				if(!m.validShot(coords[0] - 1 , coords[1] - 1, m.board1)) {
        					shoot(m, v, false, isGUI);
        				}
        			} while(m.shot(coords[0] - 1, coords[1] - 1, m.board1));
        			turn = true;
        		}
        	}
        }
        v.gameWinner(!turn);
    }
    public static void main(String [] args) {
        gameLogic();
    }
}
