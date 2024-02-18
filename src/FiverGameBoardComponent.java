import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;


/**
 * Class for generating word grid component
 */
public class FiverGameBoardComponent extends JComponent
{
	
	/* -------------------------------------------------------------------------
	 * --                                 TYPES                               --
	 * -------------------------------------------------------------------------
	 */	
	

	/* -------------------------------------------------------------------------
	 * --                            PUBLIC FIELDS                            --
	 * -------------------------------------------------------------------------
	 */	
	

	/* -------------------------------------------------------------------------
	 * --                            PRIVATE FIELDS                           --
	 * -------------------------------------------------------------------------
	 */
	
	// Reference to the parent viewer
	private FiverView gameView;
	
	// The actual gameboard
	private Rectangle[][] gameBoard;
	
	// Column the player is currently controlling. Starts at 0
	private int currCol = 0;	
	
	// The height/width of the rectangles that make up the board
	private int squareSizePx = 0;
	
	// Font for illustrating the letters
	private Font boardFont = new Font("Arial", Font.BOLD, 30);
	
	// Padding so text doesn't overwhelm the cells
	private final static int TEXT_PX_BUFFER = 14;
	
	// The offset of the grid within the component
	private final static int FRAME_BUFFER_PX = 80;
	

	/* -------------------------------------------------------------------------
	 * --                            PUBLIC METHODS                           --
	 * -------------------------------------------------------------------------
	 */
	
	/**
	 * Constructs the class
	 */
	public FiverGameBoardComponent(int boardSize, FiverView gameView)
	{
		// Initialize the gameboard
		gameBoard = new Rectangle[boardSize][boardSize];
		
		// Store reference to view
		this.gameView = gameView;
		
		// Calculate sizing for grid and letters
		calcSquareSize();
		calcApprFontSize(squareSizePx - (2 * TEXT_PX_BUFFER));
		
		// Generate the game grid based on the calculated dimensions
		genGameBoardShape();
		
		// Setup key bindings for game controls
		setBindings();
	}
	
	
	/**
	 * Paint method
	 */
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;		
		
		// Draw gameboard
		drawGameBoard(g2);
		
		// Draw words
		g2.setFont(boardFont);
		draw_Words(g2);
	}
	
	
	/**
	 * Moves the active column left
	 */
	public void moveActiveCol_Left()
	{
		currCol = (currCol + gameBoard.length - 1) % gameBoard.length;
	}
	
	
	/**
	 * Moves the active column right
	 */
	public void moveActiveColRight()
	{
		currCol = (currCol + 1) % gameBoard.length;
	}
	

	/* -------------------------------------------------------------------------
	 * --                            PRIVATE METHODS                          --
	 * -------------------------------------------------------------------------
	 */
	
	/**
	 * Calculate the appropriate font size based on the height of the cells
	 * in the gameboard. Updates internal font object to the calculated height.
	 * @param desHeight height of the cells in the gameboard
	 */
	private void calcApprFontSize(int desHeight)
	{
		// + or - the tolerable range of the calculated font
		int FONT_SIZE_RANGE = 1;
		int currHeight = getFontMetrics(boardFont).getAscent();
	    
		boolean sizeFound = false;
		
		// true means size up, false means size down
		boolean sizeDir = false;
		
		if (currHeight < (desHeight - FONT_SIZE_RANGE))
		{
			// Size up
			sizeDir = true;
		}
		else if (currHeight > (desHeight + FONT_SIZE_RANGE))
		{
			// Size down
			sizeDir = false;
		}
		else
		{
			// Already the right size
			sizeFound = true;
		}
		
	    // Loop until you find an appropriate height
		while (false == sizeFound) {
			
			int currPtSize = boardFont.getSize();
			
			if (false != sizeDir)
		   	{
				// Size up
				boardFont = boardFont.deriveFont((float)(currPtSize + 1));
		   	}
			else
			{
				// Size down
				boardFont = boardFont.deriveFont((float)(currPtSize - 1));
			}
			
			// Get new height
			currHeight = getFontMetrics(boardFont).getHeight();
			
			// Is this the right height?
			if ((currHeight >= (desHeight - FONT_SIZE_RANGE)) &&
					(currHeight <= (desHeight + FONT_SIZE_RANGE)))
			{
				sizeFound = true;
			}			
	    }
	}	

	
	/**
	 * Generate the rectangles that will make up the gameboard
	 */
	private void genGameBoardShape()
	{
		// Generate the actual gameboard from rectangles
		for(int x = 0; x < gameBoard.length; x++)
		{
			for (int y = 0; y < gameBoard.length; y++)
			{
				// Rectangles are drawn from bottom left corner
				Rectangle r = new Rectangle(FRAME_BUFFER_PX + (x * squareSizePx), 
						FRAME_BUFFER_PX + (y * squareSizePx), squareSizePx, squareSizePx);
				
				gameBoard[x][y] = r;
			}
		}
	}
	
	
	/**
	 * Helper function for drawing gameboard
	 * @param g2 graphics object for drawing
	 */
	private void drawGameBoard(Graphics2D g2)
	{
		// Draw the gameboard
		for(int x = 0; x < gameBoard.length; x++)
		{
			for (int y = 0; y < gameBoard.length; y++)
			{
				// The active column gets filled with a different color
				if (x == currCol)
				{
					g2.setColor(Color.LIGHT_GRAY);
				}
				else
				{
					g2.setColor(Color.WHITE);
				}
				
				g2.fill(gameBoard[x][y]);					
				g2.setColor(Color.BLACK);
				g2.draw(gameBoard[x][y]);
			}
		}	
	}
	
	
	/**
	 * Draws the current word arrangement
	 * @param g2 graphics object for drawing
	 */
	private void draw_Words(Graphics2D g2)
	{
		// Iterate over every char in every string
		for (int strInd = 0; strInd < gameView.currWordArrangement.length; strInd++)
		{
			String currStr = gameView.currWordArrangement[strInd];
			
			for (int charInd = 0; charInd < currStr.length(); charInd++)
			{
				// The current character being rendered. Draw method requires a string.
				String indvChar = Character.toString(currStr.charAt(charInd));
				g2.setColor(Color.BLACK);
				
				/* Need to calc starting x,y for this char based on the rectangle
				 * it's going in.
				 * 
				 * The string's position in the solution array represents the 
				 * row it will be drawn across. The current char's position
				 * represents the column.
				 */
				Rectangle currRect = gameBoard[charInd][strInd];
				
				int currRectX = currRect.x;
				int currRectY = currRect.y;
				
				/* Now center the letter */
				int currCharWidth = getFontMetrics(boardFont).stringWidth(indvChar);
				int currCharHeight = getFontMetrics(boardFont).getAscent();
				
				int currCharDrawX_Offset = (squareSizePx - currCharWidth)/2;
				
				/* Note: height looks better when ceil-ed instead of truncated by the integer 
				 * division
				 */
				int currCharDrawY_Offset = (int)Math.ceil((squareSizePx - currCharHeight)/2.0);
				
				// Offset char by the parameter TEXT_PX_BUFFER 
				g2.drawString(indvChar, currRectX + currCharDrawX_Offset, currRectY + (squareSizePx - currCharDrawY_Offset));
			}
		}
	}
	
	
	/**
	 * Calculates the size that will be used for the sides of
	 * each square. This is done programmatically in case a 
	 * different application window size is ever chosen. 
	 */
	private void calcSquareSize()
	{
		int overAllGridSize = gameView.WORD_GRID_COMP_SIZE - (2 * FRAME_BUFFER_PX);
		squareSizePx = overAllGridSize / gameBoard.length;
	}
	
	
	/**
	 * Set-up key bindings for keyboard controls
	 */
	private void setBindings()
	{
		/* Most of this code is standard fare for setting up
		 * keyboard bindings
		 */
		int condition = WHEN_IN_FOCUSED_WINDOW;
		InputMap inputMap = getInputMap(condition);
		ActionMap actionMap = getActionMap();
		
		// The controls we're mapping
		final KeyStroke[] keyStrokes = 
		{
			KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0),
			KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0),
			KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0),
			KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0)
		};
		
		/* Finalize bindings that will invoke the main
		 * callback (everything funnels through the same
		 * method).
		 */
		for (final KeyStroke keyStroke : keyStrokes)
		{
			inputMap.put(keyStroke,  keyStroke.toString());
			actionMap.put(keyStroke.toString(), new AbstractAction() {
				public void actionPerformed(ActionEvent evt)
				{
					myKeyPressed(keyStroke.getKeyCode());
				}
			});
		}
	}
	
	
	/**
	 * Call-back for when game controls are pressed
	 * @param key the key pressed
	 */
	private void myKeyPressed(int key)
	{
        switch( key )
        {
	        case KeyEvent.VK_UP:
	        	// Rotate active column down (note: rotation is opposite of the arrow key)
	        	gameView.gameController.rotateRow(currCol, FiverController.RotateDirection.DOWN);
	        	repaint();
	        	
	        	break;
	        
	        case KeyEvent.VK_DOWN:
	        	// Rotate active column up (note: rotation is opposite of the arrow key)
	        	gameView.gameController.rotateRow(currCol, FiverController.RotateDirection.UP);
	        	repaint();
	        	
	        	break;
	        	
	        case KeyEvent.VK_LEFT:
	        	// Move active col left
	        	moveActiveCol_Left();
	        	repaint();
	        	
	        	break;
	        	
	        case KeyEvent.VK_RIGHT:
	        	// Move active col right
	        	moveActiveColRight();
	        	repaint();
	        	
	        	break;
	        	
        	default:
        		// Do nothing
        		
        		break;	        		
        }
	}
	
}
