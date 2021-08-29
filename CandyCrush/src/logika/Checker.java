package logika;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Sanja Salihovic
 *
 */
class Chain
{
	public int start;
	public int end;
	public int length;
}

public class Checker 
{
	private static final int MIN_CHAIN_LENGTH = 3;
	private Board board;
	
	public Checker(Board board) 
	{
		this.board = board;
	}
	
	/**
	 * Check if two pieces are swappable
	 * @param srcRow
	 * @param srcCol
	 * @param dstRow
	 * @param dstCol
	 * @return
	 */
	public boolean canSwap(int srcRow, int srcCol, int dstRow, int dstCol) {
		if( ( (srcRow==dstRow) && (Math.abs(srcCol-dstCol)==1)) || ((Math.abs(srcRow-dstRow)==1 && srcCol==dstCol))){
			return true;
		}
		else return false;
		}
	
	/**
	 * Process selected row
	 * @param row
	 * @return
	 */
	public boolean processRow(int row)
	{
		List<Position> chain = findChainInRow(row);
		
		if(!chain.isEmpty())
		{
			board.removePieces(chain);
		}

		return !chain.isEmpty();
	}
	
	/**
	 * Process selected Column
	 * @param column
	 * @return
	 */
	public boolean processColumn(int column)
	{
		List<Position> chain = findChainInColumn(column);

		if(!chain.isEmpty())
		{
			board.removePieces(chain);
		}
		
		return !chain.isEmpty();
	}
	
	/**
	 * Find chain in row
	 * @param row
	 * @return
	 */
	private List<Position> findChainInRow(int row)
	{
		List<Piece> jewels = board.getRow(row);
		Chain chain = findChain(jewels);

		if(chain.length < MIN_CHAIN_LENGTH)
		{
			return new ArrayList<Position>();
		}

		List<Position> pieces = new ArrayList<Position>();
		
		for(int j = chain.start; j <= chain.end; j++)
		{
			pieces.add(new Position(row, j));
		}
		
		return pieces;
	}
	
	/**
	 * Find chain in column
	 * @param column
	 * @return
	 */
	private List<Position> findChainInColumn(int column)
	{
		List<Piece> jewels = board.getColumn(column);
		Chain chain = findChain(jewels);
		
		if(chain.length < MIN_CHAIN_LENGTH)
		{
			return new ArrayList<Position>();
		}
		
		List<Position> pieces = new ArrayList<Position>();
		
		for(int i = chain.start; i <= chain.end; i++)
		{
			pieces.add(new Position(i, column));
		}
		
		return pieces;
	}

	/**
	 * Check if there exists a chain
	 * @param pieces
	 * @return
	 */
	private Chain findChain(List<Piece> pieces)
	{
		Chain chain = new Chain();
		chain.length = 1;
		
		for(int i = 0; i < pieces.size() - 1; i++)
		{
			Piece currentPiece = pieces.get(i);
			Piece nextPiece = pieces.get(i + 1);
			
			if(currentPiece.equals(nextPiece))
			{
				chain.length++;
				
				if(chain.length >= MIN_CHAIN_LENGTH)
				{
					chain.end = i + 1;
					break;
				}
			}
			else
			{
				chain.start = i + 1;
				chain.length = 1;
			}
		}
		
		return chain;		
	}
	
	/**
	 * Processes the board
	 */
	public void processBoard()
	{
		boolean piecesRemoved = false;
		
		do
		{
			piecesRemoved = false;
			
			for(int i = 0; i < board.nrRows; i++)
			{
				piecesRemoved = piecesRemoved || processRow(i);
			}
	
			for(int j = 0; j < board.nrColumns; j++)
			{
				piecesRemoved = piecesRemoved || processColumn(j);
			}
		}
		while(piecesRemoved);
	}
}