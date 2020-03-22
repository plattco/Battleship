import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ModelTest {
	int [][] board1 = new int [11][11];
    int [][] board2 = new int [11][11];
    
	@Test
	public void testShot() {
		Model m = new Model(board1, board2);

		m.placeShip(1, 1, true, m.board1);
		m.placeShip(1, 5, n, m.board1);
		assertTrue(m.shot(1, 1, m.board1)); // hit
		assertTrue(m.shot(5, 5, m.board1)); // miss
		assertTrue(m.shot(1, 5, m.board1)); // hit
		assertFalse(m.shot(11, -1, m.board1)); // out of bounds
		assertFalse(m.shot(-1, 11, m.board1)); // out of bounds
		assertFalse(m.shot(5, 9, m.board1)); // out of bounds
		assertFalse(m.shot(9, 5, m.board1)); // out of bounds
		assertFalse(m.shot(8, 8, m.board1)); // miss		
	}
	
	@Test
	public void testValidShot() {
		Model m2 = new Model(board1, board2);	

		assertTrue(m2.validShot(1, 1, m2.board1)); // in bounds
		assertTrue(m2.validShot(5, 2, m2.board1)); // in bounds
		assertTrue(m2.validShot(6, 4, m2.board1)); // in bounds
		assertTrue(m2.validShot(4, 6, m2.board1)); // in bounds
		assertTrue(m2.validShot(1, 8, m2.board2)); // in bounds
		assertTrue(m2.validShot(8, 8, m2.board2)); // in bounds
		assertFalse(m2.validShot(4, 9, m2.board2)); // out of bounds
		assertFalse(m2.validShot(9, 4, m2.board2)); // out of bounds
		assertFalse(m2.validShot(1, 15, m2.board1)); // out of bounds
		assertFalse(m2.validShot(14, 1, m2.board2)); // out of bounds
		assertFalse(m2.validShot(13, 18, m2.board1)); // out of bounds
		assertFalse(m2.validShot(-3, 1, m2.board1)); // out of bounds
		assertFalse(m2.validShot(1, -15, m2.board1)); // out of bounds
		assertFalse(m2.validShot(5, 2, m2.board1)); // repeat shot
		assertFalse(m2.validShot(1, 1, m2.board1)); // repeat shot
		assertFalse(m2.validShot(8, 8, m2.board2)); // repeat shot
		assertFalse(m2.validShot(1, 8, m2.board2)); // repeat shot
	}
	
	@Test
	public void testGameOver() {
		Model m = new Model(board1, board2);

		assertTrue(m.gameOver(m.board1));
		assertTrue(m.gameOver(m.board2));
		m.placeShip(1, 1, true, m.board1);
		assertFalse(m.gameOver(m.board1));
		assertTrue(m.gameOver(m.board2));
		m.placeShip(1, 1, true, m.board2);
		assertFalse(m.gameOver(m.board1));
		assertFalse(m.gameOver(m.board2));
	}

	@Test
	//The placeShip method calls invalidShip inside so this tests both methods
	public void testPlaceShip() {
		Model m = new Model(board1, board2);

		assertTrue(m.placeShip(1, 1, true, m.board1)); // Valid ship
		assertFalse(m.placeShip(1, 1, true, m.board1)); // Ship overlaps another entirely
		assertTrue(m.placeShip(1, 1, false, m.board2)); // Valid ship
		assertFalse(m.placeShip(1, 1, false, m.board1)); // 1 index overlaps with an already placed ship
		assertFalse(m.placeShip(8, 8, true, m.board1)); // Out of bounds
		assertFalse(m.placeShip(7, 1, false, m.board1)); // Out of bounds
		assertTrue(m.placeShip(3, 4, true, m.board1)); // Valid ship
		assertTrue(m.placeShip(4, 3, true, m.board2)); // Valid ship
		assertTrue(m.placeShip(1, 5, true, m.board2)); // Valid ship
		assertTrue(m.placeShip(2, 7, false, m.board1)); // Valid ship
		assertTrue(m.placeShip(6, 6, true, m.board2)); // Valid ship
		

	}

}
