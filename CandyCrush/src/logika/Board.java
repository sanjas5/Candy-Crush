package logika;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author Sanja Salihovic
 *
 */

public class Board 
{	
	private Piece[][] board;
	public int nrRows;
	public int nrColumns;
	
	/**
	 * Constructor
	 * @param rows
	 * @param columns
	 */
	public Board(int rows, int columns) 
	{
		this.nrRows = rows;
		this.nrColumns = columns;
		this.board = createBoard();
	}
	
	/**
	 * Create Board
	 * @return
	 */
	private Piece[][] createBoard()
	{
		Piece[][] board = new Piece[nrRows][nrColumns];

		for(int row = 0; row < nrRows; row++)
		{
			for(int column = 0; column < nrColumns; column++)
			{
				board[row][column] = getRandomPiece();
			}
		}
		return board;
	}
	
	/**
	 * Get a random piece
	 * @return
	 */
	private Piece getRandomPiece()
	{
		return Piece.values()[getRandom()];
	}
	
	/**
	 * Get a random number from 1 to 6
	 * @return
	 */
	public int getRandom() {
		Random r = new Random();
		int min = 1;
		int max = 6;
		return r.nextInt(max-min) + min;
	}
	
	/**
	 * Get piece at row and column
	 * @param row
	 * @param column
	 * @return
	 */
	public Piece getPieceAt(int row, int column)
	{
		return board[row][column];
	}
	
	/**
	 * Set piece at row and column
	 * @param row
	 * @param column
	 * @param piece
	 */
	public void setPieceAt(int row, int column, Piece piece)
	{
		board[row][column] = piece;
	}

	/**
	 * Swap two pieces
	 * @param srcX
	 * @param srcY
	 * @param dstX
	 * @param dstY
	 */
	public void swapPieces(int srcX, int srcY, int dstX, int dstY)
	{
		Piece firstPiece = getPieceAt(srcX, srcY);
		Piece secondPiece = getPieceAt(dstX, dstY);
		
		setPieceAt(srcX, srcY, secondPiece);
		setPieceAt(dstX, dstY, firstPiece);
	}
	
	/**
	 * Remove pieces in chain
	 * @param chain
	 */
	public void removePieces(List<Position> chain)
	{
		for(Position p : chain)
		{
			setPieceAt(p.row, p.column, Piece.BLANK);
		}
		
		for(Position p : chain)
		{
			fillBlankAt(p.row, p.column);
		}		
	}
	
	/**
	 * Remove piece at row and column
	 * @param row
	 * @param column
	 */
	public void removePieceAt(int row, int column)
	{
		setPieceAt(row, column, Piece.BLANK);
		fillBlankAt(row, column);
	}
	
	/**
	 * Fill board with blank piece at row and column
	 * @param row
	 * @param column
	 */
	public void fillBlankAt(int row, int column)
	{
		if(row == 0)
		{
			Piece newPiece = getRandomPiece();
			setPieceAt(row, column, newPiece); 
		}
		else
		{
			Piece topPiece = getPieceAt(row - 1, column);
			setPieceAt(row - 1, column, Piece.BLANK);
			setPieceAt(row, column, topPiece); 
			removePieceAt(row - 1, column);
		}
	}
	
	/**
	 * Get row chain
	 * @param row
	 * @return
	 */
	public List<Piece> getRow(int row)
	{
		ArrayList<Piece> jewels = new ArrayList<Piece>();
		
		for(int column = 0; column < nrColumns; column++)
		{
			jewels.add(board[row][column]);
		}
		
		return jewels;
	}
	
	/**
	 * Get column chain
	 * @param column
	 * @return
	 */
	public List<Piece> getColumn(int column)
	{
		ArrayList<Piece> jewels = new ArrayList<Piece>();
		
		for(int row = 0; row < nrRows; row++)
		{
			jewels.add(board[row][column]);
		}
		
		return jewels;
	}
	
}