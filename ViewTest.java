import static org.junit.Assert.*;

import org.junit.Test;
import org.w3c.dom.Text;

public class ViewTest {
	char [][] myTextBoard = new char[11][11];
    char [][] myTextBoard2 = new char[11][11];
    char [][] oppTextBoard = new char[11][11];
	char [][] oppTextBoard2 = new char[11][11];
	int [][] board1 = new int [11][11];
    int [][] board2 = new int [11][11];
		
	public void testUpdateMyBoard() {
        View text = new View(myTextBoard, myTextBoard2, oppTextBoard, oppTextBoard2);
		Model m = new Model(board1, board2);
		
		v.updateMyBoard(m.board1, v.myTextBoard); // Displays current board(should be all .)
		m.placeShip(1, 1, true, m.board1);
		v.updateMyBoard(m.board1, v.myTextBoard); // Display board updated with one ship
		v.updateMyboard(m.board2, v.myTextBoard2); // Displays other players board(should be all .)
		m.placeShip(1, 7, false, m.board2);
		v.updateMyBoard(m.board1, v.myTextBoard); // Displays board 1(should be unchanged)
		v.updateMyBoard(m.board2, v.myTextBoard2); // Displays board 2 with newly added ship
	}

	public void testUpdateOpponentBoard() {
		View text = new View(myTextBoard, myTextBoard2, oppTextBoard, oppTextBoard2);
		Model m = new Model(board1, board2);
		m.placeShip(1, 1, true, m.board1);
		m.placeShip(1, 7, false, m.board2);

		v.updateOpponentBoard(m.board2, v.oppTextBoard); // Should display opponents board with ships hidden
		m.shot(1, 7, m.board2);
		v.updateOpponentBoard(m.board2, v.oppTextBoard); // Should display opponents board with a X for the hit
		m.shot(2, 7, m.board2);
		v.updateOpponentBoard(m.board2, v.oppTextBoard); // Should display opponents board with another X for the hit
		v.updateOpponentBoard(m.board1, v.oppTextBoard2); // Should display board for player who hasn't shot yet(all .)
		m.shot(2, 7, m.board1);
		v.updateOpponentBoard(m.board1, v.oppTextBoard2); // Should display board for second player again with O for the missed shot
		m.shot(3, 4, m.board1);
		v.updateOpponentBoard(m.board1, v.oppTextBoard2); // Should display board for second player again with another O for the missed shot
		m.shot(1, 1, m.board1);
		v.updateOpponentBoard(m.board1, v.oppTextBoard2); // Should display board for second player again with two O's for the missed shots and one new X for the hit shot
	}
	
	@Test
	public void testGetView() { //Gathers user input, not testable
		
	}
	
	public void testGetX () { //Gathers user input, not testable
		
	}

	public void testGetY () { //Gathers user input, not testable
		
	}
	
	public void testGetOrientation () { //Gathers user input, not testable
	
	}

	public void testInvalidShot () { 
		View text = new View(myTextBoard, myTextBoard2, oppTextBoard, oppTextBoard2);
		v.testInvalidShot(); // Should print a message to tell the user to make a valid shot
	}

	public void testInvalidShip () {
		View text = new View(myTextBoard, myTextBoard2, oppTextBoard, oppTextBoard2);
		v.testInvalidShip(); // Should print a message to instruct a user to place a valid ship
	}
	
	public void testPlayerTurn() {
		View text = new View(myTextBoard, myTextBoard2, oppTextBoard, oppTextBoard2);
		v.playerTurn(true); //Should print ******************Player 1's turn******************
		v.playerTurn(false); //Should print ******************Player 1's turn******************
	}
	
	public void testGameWinner () {
		View text = new View(myTextBoard, myTextBoard2, oppTextBoard, oppTextBoard2);
		v.gameWinner(true); // Should print Player 1 Wins!
		v.gameWinner(false); // Should print Player 2 Wins!
	}
	
	public void testDisplay() {

	}
}
