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
	
	// The offset of the grid within the component
	public final static int FRAME_BUFFER_PX = 45;

	
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
	
	// Font for illustrating the gameboard letters
	private Font boardFont = new Font("Arial", Font.BOLD, 30);
	
	// Font for illustrating dialogue
	private Font dialogueFont = new Font ("Arial", Font.BOLD, 30);
	
	// Padding so text doesn't overwhelm the grid cells
	private final static int GRID_TEXT_PX_BUFFER = 14;
	
	// Padding so dialogue text has some space around it
	private final static int DIAL_TEXT_PX_BUFFER = 24;
	
	// How much to dim the game board by when player solves the puzzle
	private final static float WIN_DIM_PERCENTAGE = 1.0f;
	
	// Congratulations string (in Watership Down "Flayrah" means delicious food)
	private final static String CONGRAT_STRING = "Flayrah!";
	
	// Revealed string (in Watership Down Pfeffa means cat)
	private final static String REVEAL_STRING = "Pfeffa";
	
	/* The below tuples are used to store information about the colors
	 * that will be used to render the board. 
	 * Index 0: color to use while puzzle is still being solved
	 * Index 1: color to use when puzzle is solved/revealed
	 * Index 2: color that will be used to render the board this cycle
	 */	
	private final static int PUZZLE_NOT_SOLVED_CLR_IDX = 0;
	private final static int PUZZLE_SOLVED_CLR_IDX = 1;
	private final static int CURRENT_COLOR_IDX = 2;
	
	// Color to use for the column select-er
	private Color colSelColorTuple[] = {Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY};
	
	// Color to use for the text
	private Color txtColorTuple[] = {Color.GRAY, Color.GRAY, Color.GRAY};
	
	// Color to use for the borders
	private Color borderColorTuple[] = {Color.GRAY, Color.GRAY, Color.GRAY};
	

	/* -------------------------------------------------------------------------
	 * --                            PUBLIC METHODS                           --
	 * -------------------------------------------------------------------------
	 */
	
	/**
	 * Constructs the game board
	 * @param boardSize size of the board in terms of tiles (will always be a square)
	 * @param gameView reference to the game's overarching view
	 */
	public FiverGameBoardComponent(int boardSize, FiverView gameView)
	{
		// Initialize the gameboard
		gameBoard = new Rectangle[boardSize][boardSize];
		
		// Store reference to view
		this.gameView = gameView;
		
		// Calculate colors to use when puzzle solved/revealed
		colSelColorTuple[PUZZLE_SOLVED_CLR_IDX] = 
				applyDimEffect(colSelColorTuple[PUZZLE_NOT_SOLVED_CLR_IDX], WIN_DIM_PERCENTAGE);
		txtColorTuple[PUZZLE_SOLVED_CLR_IDX] = 
				applyDimEffect(txtColorTuple[PUZZLE_NOT_SOLVED_CLR_IDX], WIN_DIM_PERCENTAGE);
		borderColorTuple[PUZZLE_SOLVED_CLR_IDX] = 
				applyDimEffect(borderColorTuple[PUZZLE_NOT_SOLVED_CLR_IDX], WIN_DIM_PERCENTAGE);
		
		// Use black border and text for active puzzle 
		txtColorTuple[PUZZLE_NOT_SOLVED_CLR_IDX] = 
				applyDirBrightness(txtColorTuple[PUZZLE_NOT_SOLVED_CLR_IDX], 0.0f);
		borderColorTuple[PUZZLE_NOT_SOLVED_CLR_IDX] = 
				applyDirBrightness(borderColorTuple[PUZZLE_NOT_SOLVED_CLR_IDX], 0.0f);

		
		// Calculate sizing for grid and letters
		calcSquareSize();
		boardFont = calcApprFontSize(squareSizePx - (2 * GRID_TEXT_PX_BUFFER), boardFont);
		
		// Calculate sizing for dialogue
		dialogueFont = calcApprFontSize(FRAME_BUFFER_PX - DIAL_TEXT_PX_BUFFER, dialogueFont);
		
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
		
		// Determine which color palette to use
		updColorsToUse();
		
		// Draw game board
		drawGameBoard(g2);
		
		// Draw words
		draw_Words(g2);
	}
	
	
	/** 
	 * Helper function that updates the current colors to use to render the game board
	 */
	public void updColorsToUse()
	{
		// If puzzle solved or revealed, use the dimmer color palette
		if ( (false != gameView.gameController.is_Solved) ||
			 (false != gameView.gameController.revealGame) )
		{
			colSelColorTuple[CURRENT_COLOR_IDX] = colSelColorTuple[PUZZLE_SOLVED_CLR_IDX];
			txtColorTuple[CURRENT_COLOR_IDX] = txtColorTuple[PUZZLE_SOLVED_CLR_IDX];
			borderColorTuple[CURRENT_COLOR_IDX] = borderColorTuple[PUZZLE_SOLVED_CLR_IDX];
		}
		else
		{
			colSelColorTuple[CURRENT_COLOR_IDX] = colSelColorTuple[PUZZLE_NOT_SOLVED_CLR_IDX];
			txtColorTuple[CURRENT_COLOR_IDX] = txtColorTuple[PUZZLE_NOT_SOLVED_CLR_IDX];
			borderColorTuple[CURRENT_COLOR_IDX] = borderColorTuple[PUZZLE_NOT_SOLVED_CLR_IDX];
		}
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
	 * Helper function for calculating the max font size that fits
	 * within the specified desired height. Needed because font size
	 * is expressed as points instead of pixels.
	 * @param desHeight desired height of the font in pixels
	 * @param f the font object to apply size to
	 * @return font object with the desired height
	 */
	private Font calcApprFontSize(int desHeight, Font f)
	{
		// + or - the tolerable range of the calculated font
		int FONT_SIZE_RANGE = 3;
		int currHeight = getFontMetrics(f).getAscent();
	    
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
			
			int currPtSize = f.getSize();
			
			if (false != sizeDir)
		   	{
				// Size up
				f = f.deriveFont((float)(currPtSize + 1));
		   	}
			else
			{
				// Size down
				f = f.deriveFont((float)(currPtSize - 1));
			}
			
			// Get new height
			currHeight = getFontMetrics(f).getHeight();
			
			// Is this the right height?
			if ((currHeight >= (desHeight - FONT_SIZE_RANGE)) &&
					(currHeight <= (desHeight + FONT_SIZE_RANGE)))
			{
				sizeFound = true;
			}			
	    }
		
		return f;
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
	 * Helper function for drawing game board
	 * @param g2 graphics object for drawing
	 */
	private void drawGameBoard(Graphics2D g2)
	{
		// Draw the game board
		for(int x = 0; x < gameBoard.length; x++)
		{
			for (int y = 0; y < gameBoard.length; y++)
			{
				// The active column gets filled with a different color
				if (x == currCol)
				{
					g2.setColor(colSelColorTuple[CURRENT_COLOR_IDX]);
				}
				else
				{
					g2.setColor(Color.WHITE);
				}
				
				g2.fill(gameBoard[x][y]);					
				g2.setColor(borderColorTuple[CURRENT_COLOR_IDX]);
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
		String[] currWordArrangement = gameView.gameController.gameModel.currGameBoard;
		
		// Iterate over every char in every string
		g2.setFont(boardFont);
		
		for (int strInd = 0; strInd < currWordArrangement.length; strInd++)
		{
			String currStr = currWordArrangement[strInd];
			
			for (int charInd = 0; charInd < currStr.length(); charInd++)
			{
				// The current character being rendered. Draw method requires a string.
				String indvChar = Character.toString(currStr.charAt(charInd));
				g2.setColor(txtColorTuple[CURRENT_COLOR_IDX]);
				
				/* Need to calc starting x,y for this char based on the rectangle
				 * it's going in.
				 * 
				 * The string's position in the current play array represents the 
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
		
		// If solved or revealed, display respective dialogue
		g2.setFont(dialogueFont);
		int solvedOffset = (int)Math.ceil((FRAME_BUFFER_PX - DIAL_TEXT_PX_BUFFER)/2.0);	
		
		if (false != gameView.gameController.is_Solved)
		{
			g2.setColor(Color.BLACK);					
			g2.drawString(CONGRAT_STRING, FRAME_BUFFER_PX, FRAME_BUFFER_PX - solvedOffset);
		}
		
		if (false != gameView.gameController.revealGame)
		{
			g2.setColor(Color.BLACK);
			g2.drawString(REVEAL_STRING, FRAME_BUFFER_PX, FRAME_BUFFER_PX - solvedOffset);
		}
	}
	
	
	/**
	 * Calculates the size that will be used for the sides of
	 * each square. This is done programmatically in case a 
	 * different application window size is ever chosen. 
	 */
	private void calcSquareSize()
	{
		int overAllGridSize = FiverView.WORD_GRID_COMP_SIZE - (2 * FRAME_BUFFER_PX);
		squareSizePx = overAllGridSize / gameBoard.length;
	}
	
	
	/**
	 * Helper function for dimming a color by a specified weight
	 * @param c the color to dim
	 * @param perc the percent amount to dim by (e.g. 0.75 means a 25% reduction in brightness)
	 * @return the result of dimming the color by the specified amount
	 */
	private Color applyDimEffect(Color c, float perc)
	{
		// Range check; percent needs to be between 0 and 1. If out of range,
		// then don't modify brightness
		if (perc > 1.0f || perc < 0.0f)
		{
			perc = 1.0f;
		}
		
		// Convert color to hue/saturation/brightness space and then apply percentage to b
		float hsbVals[] = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
		Color dimColor = Color.getHSBColor( hsbVals[0], hsbVals[1], perc * hsbVals[2]);
		
		return dimColor;
	}
	
	
	/**
	 * Helper function for modifying a color to use the exact specified brightness
	 * @param c the color to modify
	 * @param brightness a brightness value in the range [0,1]
	 * @return the result of applying the specified brightness to the color
	 */
	private Color applyDirBrightness(Color c, float brightness)
	{
		// Convert color to hue/saturation/brightness space
		float hsbVals[] = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
		
		// Range check; brightness needs to be between 0 and 1. 
		// If out of range, don't apply a change
		if (brightness > 1.0f || brightness < 0.0f)
		{
			brightness = hsbVals[2];
		}
		
		// Apply brightness along color/saturation
		Color retColor = Color.getHSBColor( hsbVals[0], hsbVals[1], brightness);
		
		return retColor;
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
		// Was a relevant key pressed?
		boolean relKeyPressed = false;
		
		// Only allowed to play if the puzzle isn't solved or revealed
		if ( (false == gameView.gameController.is_Solved) &&
			 (false == gameView.gameController.revealGame) )
		{		
	        switch( key )
	        {
		        case KeyEvent.VK_UP:
		        	// Rotate active column down (note: rotation is opposite of the arrow key)
		        	gameView.gameController.rotateRow(currCol, FiverController.RotateDirection.DOWN);
		        	relKeyPressed = true;
		        	
		        	break;
		        
		        case KeyEvent.VK_DOWN:
		        	// Rotate active column up (note: rotation is opposite of the arrow key)
		        	gameView.gameController.rotateRow(currCol, FiverController.RotateDirection.UP);
		        	relKeyPressed = true;
		        	
		        	break;
		        	
		        case KeyEvent.VK_LEFT:
		        	// Move active col left
		        	moveActiveCol_Left();
		        	relKeyPressed = true;
		        	
		        	break;
		        	
		        case KeyEvent.VK_RIGHT:
		        	// Move active col right
		        	moveActiveColRight();
		        	relKeyPressed = true;
		        	
		        	break;
		        	
	        	default:
	        		// Do nothing
	        		
	        		break;	        		
	        }
	        
	        // If a relevant key was pressed then perform necessary back-end logic
	        if (false != relKeyPressed)
	        {
	        	// Check whether puzzle solved
	        	gameView.gameController.checkForSolved();
	        	
	        	repaint();
	        }
		}
	}
	
}
