package logika;

public class Position {
	public int row;
	public int column;
	
	/**
	 * Constructor
	 * @param row
	 * @param column
	 */
	public Position(int row, int column) 
	{
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Gives position in string format
	 */
	public String toString() 
	{
		return row + ":" + column;
	}
}
