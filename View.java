import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
public class View {
    static Scanner in = new Scanner(System.in);
    char [][] myTextBoard;
    char [][] myTextBoard2;
    char [][] oppTextBoard;
    char [][] oppTextBoard2;
    
    //Allowed?
    private static int[] clickCoordinates = new int[2];
    private static ArrayList<int[]> allClickCoordinates = new ArrayList<int[]>();
    private static JFrame frame;
    private static JPanel buttonPanel;
    private static JPanel containerPanel;
    private static JPanel masterPanel;
    private static JButton button;

    /**
     * Constructor for the view
     * @param myTextBoard player 1's board from their perspective
     * @param myTextBoard2 player 2's board from their perspective
     * @param oppTextBoard player 1's board from player 2's perspective
     * @param oppTextBoard2 player 2's board from player 1's perspective
     */
    public View() {
        
    }
    public View(char [][] myTextBoard, char [][] myTextBoard2, char [][] oppTextBoard, char [][] oppTextBoard2) { 
        this.myTextBoard = myTextBoard;
        this.oppTextBoard = oppTextBoard;
        this.myTextBoard2 = myTextBoard2;
        this.oppTextBoard2 = oppTextBoard2;
    }

    /**
     * Updates the players board after each shot or ship placement
     * @param arr board being altered
     * @param textBoard character representation of the board being altered
     * @return 
     */
    public static int[] updateMyBoard(final int[][] arr, char [][] textBoard, boolean isGUI) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] == 0) {
                    textBoard[i][j] = '.';
                } else if (arr[i][j] == 1) {
                    textBoard[i][j] = 'O';
                } else if (arr[i][j] == 2) {
                    textBoard[i][j] = 'S';
                } else if (arr[i][j] == 3) {
                    textBoard[i][j] = 'X';
                }
            }
        }
        
        if (!isGUI) {
        	display(textBoard);
            return null;
        }
        else {
        	//int[] coordinates = {1, 2 ,3};
        	updateButtons(frame, buttonPanel, containerPanel, masterPanel, button, textBoard);
        	return load(buttonPanel, containerPanel, button, textBoard, true);
        	
        	//check this out later //readButtons(textBoard);
        	//placeShips(buttonPanel, containerPanel);
        	//clickCoordinates = placeShipsPoint(buttonPanel); //this isnt what I want
        	//for (int i : clickCoordinates)
        		//System.out.println(i);
        	
        }
    }

    /**
     * Updates the board being altered for the user to shoot at
     * @param arr board being altered
     * @param textBoard character representation of board being altered
     */
    public static int[] updateOpponentBoard(final int[][] arr, char [][] textBoard, boolean isGUI) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                int test = arr[i][j];
                if (arr[i][j] == 0 || arr[i][j] == 2) {
                    textBoard[i][j] = '.';
                } else if (arr[i][j] == 1) {
                    textBoard[i][j] = 'O';
                } else if (arr[i][j] == 3) {
                    textBoard[i][j] = 'X';
                }
            }
        }
        if (!isGUI) {
        	display(textBoard);
            return null;
        }
        else {
        	//int[] coordinates = {1, 2 ,3};
        	updateButtons(frame, buttonPanel, containerPanel, masterPanel, button, textBoard);
        	return load(buttonPanel, containerPanel, button, textBoard, false);
        }
    }

    /**
     * Allows user to decide between gui or text based view
     * @return user input of y or n on gui view
     */
    public String getView() {
        System.out.println("Would you like gui view?(y/n) ");
        return in.next();
    }

    /**
     * Gets the x coordinate of a position from the user
     * @return the x coordinate
     */
    public int getX() {
        System.out.println("Enter an x coordinate(1-8): ");
        return in.nextInt();
    }
    

    /**
     * Gets the y coordinate of a position from the user
     * @return the y coordinate
     */
    public int getY() {
        System.out.println("Enter a y coordinate(1-8): ");
        return in.nextInt();
    }

    /**
     * Gets whether the ship being placed is vertical or not
     * @return true if vertical, false if horizontal
     */
    public boolean getOrientation() {
        System.out.println("Would you like this ship to be placed vertically?(y/n): ");
        if(in.next().equals("y")) {
            return true;
        }
        return false;
    }

    /**
     * Prints a warning if a user makes an invalid shot
     */
    public void invalidShot() {
        System.out.println("Make sure you are shooting in bounds and the space hasn't already been hit!");
    }

    /**
     * Prints a warning if the user places an invalid ship
     */
    public void invalidShip() {
        System.out.println("Please make a valid ship placement(in bounds and no overlapping)");
        System.out.println("Ships are placed from the topmost coordinate if vertical and from the leftmost coordinate if horizontal");
    }

    /**
     * Prints out which players turn it is
     * @param turn true for  player 1 false for player 2
     */
    public void playerTurn(boolean turn) {
        if(turn) {
            System.out.println("******************Player 1's turn******************");
        } else {
            System.out.println("******************Player 2's turn******************");
        }
    }

    /**
     * Prints out a message for the winner of the game
     * @param winner true for player 1 false for player 2
     */
    public void gameWinner(boolean winner) {
        if(winner) {
            System.out.println("Player 1 Wins!");
        }
        else {
            System.out.println("Player 2 Wins!");
        }
    }

    /**
     * Prints all of the boards out for any players turn
     * @param textBoard the board to be printed
     */
    public static void display(char [][] textBoard) {
        int k = 1;
        System.out.print("   ");
        for(int n = 1; n < 9; n++) {
            System.out.print(n + "  ");
        }
        System.out.println();
        for(int i = 0; i < 8; i++) {
            System.out.print(k + "  ");
            for(int j = 0; j < 8; j++) {
                System.out.print(textBoard[j][i] + "  ");
            }
            System.out.println();
            k++;
        }
    }
    
    //////////////////////////////GUI based methods here//////////////////////////////////////////////////////////////
    public void gui(char [][] myTextBoard, char [][] myTextBoard2, char [][] oppTextBoard, char [][] oppTextBoard2) {
    	
    	frame = new JFrame("Battleship!");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    	JLabel label1 = new JLabel("Test");
    	buttonPanel = new JPanel();
    	containerPanel = new JPanel();
    	masterPanel = new JPanel();

    	//JButton turnEnder = new JButton("Done"); // user selects this to end their turn DONT LEAVE HERE
    	JButton button = null; // each square of the board

    	// Declaring types of layouts
    	containerPanel.setLayout(new FlowLayout());
    	masterPanel.setLayout(new FlowLayout());
    	buttonPanel.setLayout(new GridLayout(8,8));

    	//Find a better home //placeShips(buttonPanel, containerPanel);

    	//Find a better home //updateButtons(frame, buttonPanel, turnEnder, testBoard2);
    	
    	/*
    	        // normal board usage
    			for (int i = 0; i < 8; i++) {
    				for (int j = 0; j < 8; j++) {
    					button = new JButton("-");
    					// reveal value in button on click
    					reveal(button, testBoard[i][j]);
    					// add button to panel
    					buttonPanel.add(button);
    				}
    			}
    	 */
    	buttonPanel.setPreferredSize(new Dimension(700, 700));
    	masterPanel.add(buttonPanel);

    	//Adding text
    	updateText(label1, "Update text");

    	//Adding panels together into frame, setting frame
    	//containerPanel.add(turnEnder);
    	containerPanel.add(label1);
    	masterPanel.add(containerPanel, BorderLayout.SOUTH);
    	frame.getContentPane().add(masterPanel, BorderLayout.SOUTH);
    	frame.pack();
    	frame.setVisible(true);

    	// updating screen after each turn
    	// updateButtons(frame, buttonPanel, turnEnder, testBoard2);
    }
    
    public static void updateText(JLabel label, String text) {
		label.setText("<html><h1>" + text + "</h1></html>");
	}
    
    /**
     * 
     * @param buttonPanel
     * @param containerPanel
     * @param button
     * @param currentBoard
     * @return
     *
    public static int[] aim(JPanel buttonPanel, JPanel containerPanel, JButton button, char[][] currentBoard) { 
    	buttonPanel.removeAll();
    	buttonPanel.repaint();
    	
    	JButton select = new JButton("Give coordinate to shoot!");
    	containerPanel.add(select);
    	
    	JTextField xField = new JTextField(7); 
        JTextField yField = new JTextField(7);
        xField.setText("X-Value");
        yField.setText("Y-Value");
        containerPanel.add(xField);
        containerPanel.add(yField);
    } 
    
    /**
     * Load in users choice for their ship placements
     * @param buttonPanel
     * @param containerPanel
     * @param button
     * @param currentBoard
     * @return
     */
    public static int[] load(JPanel buttonPanel, JPanel containerPanel, JButton button, char[][] currentBoard, boolean placing) { 
    	buttonPanel.removeAll();
    	buttonPanel.repaint();
    	
    	JButton select = new JButton("Place Ship At These Coordinates");
    	containerPanel.add(select);
    	
    	JRadioButton vertical = new JRadioButton("Vertical", true);
        JRadioButton horizontal = new JRadioButton("Horizontal");
        //Add buttons to a group
        ButtonGroup orientation = new ButtonGroup();
        //Selector for orientation
        orientation.add(vertical);
        orientation.add(horizontal);
        containerPanel.add(vertical);
        containerPanel.add(horizontal);
        
        JTextField xField = new JTextField(7); 
        JTextField yField = new JTextField(7);
        xField.setText("X-Value");
        yField.setText("Y-Value");
        containerPanel.add(xField);
        containerPanel.add(yField);
        
    	for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				button = new JButton("" + currentBoard[i][j]);
				// add button to panel
				buttonPanel.add(button);
				System.out.println("t");
				
				//Update frame
				masterPanel.add(buttonPanel);
				frame.getContentPane().add(masterPanel, BorderLayout.SOUTH);
				frame.pack();
		    	frame.setVisible(true);
			}
		}
    	//int[] coordinates = {1, 2 ,3};
    	return setCoordinates(select, xField, yField, vertical, horizontal, placing);
    }
    
    static int x = -1;
    static int y = -1;
    static int isVertical;
    
    //WORKS
    public static int[] setCoordinates(JButton select, JTextField xField, JTextField yField, 
    		JRadioButton vertical, JRadioButton horizontal, boolean placing) {
    	//if (placing) {
    		x = -1;
    		y = -1;
    	//}
    	while(x == -1 || y ==-1) {
    	select.addActionListener(
     			new ActionListener() {
     				public void actionPerformed(ActionEvent e) {
     					
     					//flipped becasue x/y reversed
     					String yInput = xField.getText();
     					String xInput = yField.getText();
     					
     					getX(xInput);
     					getY(yInput);
     					
     					if (vertical.isSelected()) {
     						isVertical = 1;
     						System.out.println(""); //taking out this print will break the whole code. So don't.
     					}
     					if (horizontal.isSelected()) {
     						isVertical = 0;
     					}
     				}
     			});
    	}
    	
    	int[] coordPair = {x, y, isVertical};
    	//if(placing) {
    		x = -1;
    		y = -1;
    	//}
    	return coordPair;
    }
    
    public static void getX(String xInput) {
    	x = Integer.parseInt(xInput);
    }
    
    public static void getY(String yInput) {
    	y = Integer.parseInt(yInput);
    }
    
    public static void updateButtons(JFrame frame, JPanel buttonPanel, JPanel containerPanel, 
    		JPanel masterPanel, JButton button, char[][] charBoard) {

    	buttonPanel.removeAll();
    	containerPanel.removeAll();
    	buttonPanel.repaint();
    	containerPanel.repaint();
    	for (int i = 0; i < 8; i++) {
    		for (int j = 0; j < 8; j++) {
    			button = new JButton("" + charBoard[j][i]);
    			buttonPanel.add(button);
    		}
    	}
    	masterPanel.add(buttonPanel);
    	frame.getContentPane().add(masterPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }
    
//////////////////////////////End GUI based methods//////////////////////////////////////////////////////////////
}
