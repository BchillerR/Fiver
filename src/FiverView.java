import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;


/**
 * Viewer class
 */
public class FiverView extends JFrame {	
	
	/* -------------------------------------------------------------------------
	 * --                                 TYPES                               --
	 * -------------------------------------------------------------------------
	 */	
	
	
	/* -------------------------------------------------------------------------
	 * --                            PUBLIC FIELDS                            --
	 * -------------------------------------------------------------------------
	 */
	
	// Reference for the game's controller
	public FiverController gameController;
	
	// Will hold a reference to the current arrangement of words
	public String[] currWordArrangement;
	
	// Logo component's width
	public static final int LOGO_COMPONENT_SIZE_WTH = 800;
	
	// Logo component's height
	public static final int LOGO_COMPONENT_SIZE_HGT = 200;
	
	// Size of the component for the word grid (will always be a square)
	public static final int WORD_GRID_COMP_SIZE = 600;
	
	// Component width for the button panel
	public static final int BUTTON_COMPONENT_SIZE_WTH = 200;
	
	// Component height for the button panel
	public static final int BUTTON_COMPONENT_SIZE_HGT = 600;
	
	
	/* -------------------------------------------------------------------------
	 * --                            PRIVATE FIELDS                           --
	 * -------------------------------------------------------------------------
	 */
	
	// Size of the application's frame (will always be a square)
	private static final int FRAME_SIZE = 800;
	
	// Button for resetting the gameboard
	private JButton randomizeButton;
	
	// Button for starting a new game with different words
	private JButton nextButton;
	
	// Reference to gameboard grid
	private FiverGameBoardComponent gameBoardGrid;
	
	
	/* -------------------------------------------------------------------------
	 * --                            PUBLIC METHODS                           --
	 * -------------------------------------------------------------------------
	 */
	
	/**
	 * The primary constructor
	 * @param gameController reference to the game's controller
	 * @param initWordArrangement initial arrangement of words 
	 */
	public FiverView(FiverController gameController, String[] initWordArrangement)
	{
		this.gameController = gameController;
		this.currWordArrangement = initWordArrangement;
		
		initGameBoard();
	}	
	
	
	/**
	 * Initializes the GUI for the game.
	 */
	public void initGameBoard()
	{
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WORD_GRID_COMP_SIZE, WORD_GRID_COMP_SIZE);
		setTitle("Fiver");
		
		gameBoardGrid = new FiverGameBoardComponent(gameController.BOARD_SIZE, this);
		gameBoardGrid.setFocusable(true);
		gameBoardGrid.setPreferredSize(new Dimension(WORD_GRID_COMP_SIZE, WORD_GRID_COMP_SIZE));
		add(gameBoardGrid, BorderLayout.CENTER);
		setVisible(true);
	}
	
	
	/**
	 * Setter function to give the graphical layer an account of
	 * what the current layout of words is
	 * @param currWordArrangement the current layout of words
	 */
	public void setCurrWordLayout(String[] currWordArrangement)
	{
		this.currWordArrangement = currWordArrangement;
	}
	
	
	/* -------------------------------------------------------------------------
	 * --                            PRIVATE METHODS                          --
	 * -------------------------------------------------------------------------
	 */
	

}
