package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logika.*;
/**
 * 
 * @author Sanja Salihovic
 *
 */
public class BoardGUI extends JPanel implements ActionListener 
{	
	private Board board;
	private Checker checker;
	private Position firstClick;
	private Position secondClick;
	private JButton[][] buttons;
	private JLabel pointsLabel;
	private int points;
	private boolean first = false,second = false;
	
	/**
	 * Constructor
	 * @param rows
	 * @param columns
	 */
	public BoardGUI(int rows, int columns) 
	{
		createNewBoard(rows, columns);
		createUI();
		refreshUI();
	}
	
	/**
	 * Create UI
	 */
	private void createUI()
	{
		JPanel boardPanel = createBoardPanel();
		JPanel topPanel = createTopPanel();
		boardPanel.setBackground(Color.decode("#003049"));
		boardPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		topPanel.setBackground(Color.decode("#003049"));
		BorderLayout mainLayout = new BorderLayout();
		setLayout(mainLayout);
		add(boardPanel, BorderLayout.CENTER);
		add(topPanel,BorderLayout.NORTH);
	}
	
	/**
	 * Create Top panel
	 * @return
	 */
	private JPanel createTopPanel()
	{
		this.pointsLabel = new JLabel("Points:");
		pointsLabel.setBackground(Color.decode("#003049"));
		pointsLabel.setForeground(Color.decode("#f0efeb"));
		JButton newGame = new JButton("New Game");
		newGame.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				createNewBoard(board.nrRows, board.nrColumns);
				refreshUI();
			}
		});
		
		/**
		 * New Game Button Look
		 */
		newGame.setOpaque(false);
		newGame.setContentAreaFilled(false);
		newGame.setForeground(Color.decode("#f0efeb"));
		newGame.setFocusable(false);
		
		BorderLayout layout = new BorderLayout();
		JPanel topPanel = new JPanel(layout);
		topPanel.add(pointsLabel, BorderLayout.WEST);
		topPanel.add(newGame, BorderLayout.EAST);
		topPanel.setBorder(BorderFactory.createEmptyBorder(10,20,0,20));
		return topPanel;
	}
	
	/**
	 * Create Board
	 * @param rows
	 * @param columns
	 */
	public void createNewBoard(int rows, int columns)
	{
		this.board = new Board(rows, columns);
		this.checker = new Checker(board);
		checker.processBoard();
		this.points = 0;
	}
	
	/**
	 * Update Points
	 */
	private void updatePoints()
	{
		pointsLabel.setText("Points: " + points);
	}
	
	/**
	 * Refresh UI
	 */
	private void refreshUI()
	{
		for(int row = 0; row < board.nrRows; row++)
		{
			for(int column = 0; column < board.nrColumns; column++)
			{
				Piece piece = board.getPieceAt(row, column);
				Color buttonColor = getPieceColor(piece);
				buttons[row][column].setBackground(buttonColor);
			}
		}
		updatePoints();
	}
	
	/**
	 * Create Board Panel
	 * @return
	 */
	private JPanel createBoardPanel()
	{
		int rows = board.nrRows;
		int columns = board.nrColumns;
		
		GridLayout layout = new GridLayout(rows, columns);
		JPanel panel = new JPanel(layout);
		
		this.buttons = new JButton[rows][columns];
		
		for(int row = 0; row < rows; row++)
		{
			for(int column = 0; column < columns; column++)
			{
				buttons[row][column] = new JButton();
				buttons[row][column].setActionCommand(String.valueOf(row) + " " + String.valueOf(column));
				buttons[row][column].addActionListener(this);
				buttons[row][column].setBorder(BorderFactory.createLineBorder(Color.decode("#003049"),1));
				panel.add(buttons[row][column]);
			}
		}
		return panel;
	}
	
	/**
	 * Get Piece Color
	 * @param piece
	 * @return
	 */
	private Color getPieceColor(Piece piece)
	{
		final Color[] colors = new Color[]
	    {
			Color.gray, Color.decode("#a0c4ff"), Color.decode("#ffadad"), 
			Color.decode("#ffd6a5"), Color.decode("#caffbf"), 
			Color.decode("#bdb2ff"), Color.decode("#fdffb6")
		};

		return colors[piece.ordinal()];
	}
	
	/**
	 * Sets the first piece after click
	 * @param row
	 * @param col
	 * @return
	 */
	public Position setFirstPiece(int row,int col) {
		Color c = buttons[row][col].getBackground();
		first = true;
		firstClick = new Position(row,col);
		firstClick.row = row;
		firstClick.column = col;
		buttons[row][col].setBackground(c.brighter());
		return firstClick;
	}
	
	/**
	 * Sets the second piece after click
	 * @param row
	 * @param col
	 * @return
	 */
	public Position setSecondPiece(int row,int col) {
		second = true;
		secondClick = new Position(row,col);
		secondClick.row = row;
		secondClick.column = col;
		return secondClick;
	}

	/**
	 * Set Piece on click
	 * @param row
	 * @param col
	 */
	public void setPiece(int row, int col) {
		if(!first){	
			setFirstPiece(row,col);
			return;
		}
		if(first && !second){	
			setSecondPiece(row,col);
			return;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String[] cmd = e.getActionCommand().split(" "); 
		int cmdrow = Integer.parseInt(cmd[0]);
        int cmdcol = Integer.parseInt(cmd[1]);
        setPiece(cmdrow,cmdcol);
        
		 if(first && second) {
    		if(!(checker.canSwap(firstClick.row, firstClick.column, secondClick.row,secondClick.column)))
    		{
    			System.out.println("Bad move");
    			firstClick = null;
    			secondClick = null;
    			first = false;
    			second = false;
    			return;	
    		}
    		else{
    			board.swapPieces(firstClick.row, firstClick.column, secondClick.row, secondClick.column);
    			firstClick=null;
        		secondClick=null;
        		first = false;
    			second = false;
    			checker.processBoard();
    			refreshUI();
    			points+=500;
    			updatePoints();
    		}
    	}	
	}	
}