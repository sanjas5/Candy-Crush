package konzola;


import java.util.Arrays;
import java.util.Scanner;
import logika.Board;
import logika.Checker;
import logika.Piece;
import logika.Position;

public class Igra {
	private static Board board;
	private static Checker checker;
	private static Position firstClick;
	private static Position secondClick;
	private static int[][] buttons;
	private static int points;
	private static boolean first = false;
	private static boolean second = false;
	private static boolean start = true;
	
	
	/**
	 * Update Points
	 */
	private static void updatePoints()
	{
		String pointsLabel = "Points: " + points;
	}
	
	/**
	 * Refresh UI
	 */
	private static void refreshUI()
	{
		for(int i = 0; i < board.nrRows; i++)
		{
			for(int j = 0; j < board.nrColumns; j++)
			{
				Piece piece = board.getPieceAt(i, j);
				int buttonValue = getPieceValue(piece);
				buttons[i][j] = buttonValue;	
			}
		}

		updatePoints();
	}
	
	/**
	 * Create Board
	 */
	private static void createBoard()
	{
		int rows = board.nrRows;
		int columns = board.nrColumns;
		
		buttons = new int[rows][columns];
		
		for(int row = 0; row < rows; row++)
		{
			for(int column = 0; column < columns; column++)
			{
				buttons[row][column] = 0;
			}
		}
		
	}
	
	/**
	 * Get Piece Value
	 * @param piece
	 * @return
	 */
	private static int getPieceValue(Piece piece)
	{
		final int[] values = new int[] {0,1,2,3,4,5,6};
		return values[piece.ordinal()];
	}
	
	
	/**
	 * Print Board
	 */
	public static void printBoard() {
        for (int[] red : buttons ) System.out.println(Arrays.toString(red));
        System.out.println("");
    }
	
	/**
	 * Set First Piece
	 * @param row
	 * @param col
	 * @return
	 */
	public static Position setFirstPiece(int row,int col) {
		first = true;
		firstClick = new Position(row,col);
		firstClick.row = row;
		firstClick.column = col;
		return firstClick;
	}
	/**
	 * Set Second Piece
	 * @param row
	 * @param col
	 * @return
	 */
	public static Position setSecondPiece(int row,int col) {
		second = true;
		secondClick = new Position(row,col);
		secondClick.row = row;
		secondClick.column = col;
		return secondClick;
	}
	
	/**
	 * Set Piece at row and column
	 * @param row
	 * @param col
	 */
	public static void setPiece(int row, int col) {
		if(!first){		
			setFirstPiece(row,col);
			return;
		}
		if(first && !second){	
			setSecondPiece(row,col);
			return;	
		}
	}

	/**
	 * Get user input
	 * @return
	 */
	 private static String getInput() {
	    	Scanner sc = new Scanner(System.in);
			String returnString = "";
			try {
				returnString = sc.nextLine();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return returnString;
	    }
	
	public static void main(String[] args) {
		board = new Board(6,6);
		createBoard();
		checker = new Checker(board);
		checker.processBoard();
		points = 0;
		System.out.println("Candy Crush\n");
   	 	System.out.println("To end the game: 'quit'");
   	 	System.out.println("To start a new game: 'new game'");
   	 	System.out.println("");
		refreshUI();
		printBoard();
		
		while (start) {
    		System.out.println("Enter a value: ");
 			String x = getInput();
 				if(x.equals("quit")) { 
 				start = false;
 				System.out.println("Ended the game");
 				System.exit(0);
 				break;
 			}
 			else if(x.equals("new game")) {
 				System.out.println("Started a new game.\n");
 				main(args);
 				break;
 			}
 			else{
 				String y = getInput();
 				if(y.equals("quit")) { 
 					start = false;
 					System.out.println("Ended the game");
 	 				System.exit(0);
 					break;
 				}
 				else if(y.equals("new game")) {
 					System.out.println("Started a new game.\n");
 					main(args);
 					break;
 				}
 				setPiece(Integer.parseInt(x),Integer.parseInt(y));
 					if(first && second) {
 			    		if(!(checker.canSwap(firstClick.row, firstClick.column, secondClick.row,secondClick.column)))
 			    		{
 			    			System.out.println("Bad move");
 			    			firstClick = null;
 			    			secondClick = null;
 			    			first = false;
 			    			second = false;
 			    			printBoard();
 			    			
 			    		}
 			    		else{
 			    			board.swapPieces(firstClick.row, firstClick.column, secondClick.row, secondClick.column);
 			    			System.out.println("Swapped: " + firstClick.row + 
 			    					"," + firstClick.column
 			    					+ " with " + secondClick.row + "," 
 			    					+ secondClick.column);
 			    			firstClick=null;
 			        		secondClick=null;
 			        		first = false;
 			    			second = false;
 			    			updatePoints();
 			    			checker.processBoard();
 			    			refreshUI();
 			    			printBoard();
 			    			points+=500;
 			    			updatePoints();
 			    			System.out.println("Points: " + points);
 			    			
 			    		}
 			    		
 			    	}
    	 }
		
	}

}
}
