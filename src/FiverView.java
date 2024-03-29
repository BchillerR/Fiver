import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


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
	
	// Logo component's width
	public static final int LOGO_COMPONENT_SIZE_WTH = 640;
	
	// Logo component's height
	public static final int LOGO_COMPONENT_SIZE_HGT = 140;
	
	// Size of the component for the word grid (will always be a square)
	public static final int WORD_GRID_COMP_SIZE = 500;
	
	// Component width for the button panel
	public static final int SIDE_COMPONENT_SIZE_WTH = 140;
	
	// Component height for the button panel
	public static final int BUTTON_COMPONENT_SIZE_HGT = 500;
	
	// Reference to gameboard grid
	public FiverGameBoardComponent gameBoardGrid;
	
	
	/* -------------------------------------------------------------------------
	 * --                            PRIVATE FIELDS                           --
	 * -------------------------------------------------------------------------
	 */
	
	// Size of the application's frame (will always be a square)
	private static final int FRAME_SIZE = 640;
	
	
	/* -------------------------------------------------------------------------
	 * --                            PUBLIC METHODS                           --
	 * -------------------------------------------------------------------------
	 */
	
	/**
	 * The primary constructor
	 * @param gameController reference to the game's controller
	 */
	public FiverView(FiverController gameController)
	{
		this.gameController = gameController;
		
		initGameBoard();
	}	
	
	
	/**
	 * Initializes the GUI for the game.
	 */
	public void initGameBoard()
	{
		// Set up frame
		setLayout(new BorderLayout());
		getContentPane().setSize(FRAME_SIZE, FRAME_SIZE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Fiver");		
		
		// Build the word grid component first
		gameBoardGrid = new FiverGameBoardComponent(FiverController.BOARD_SIZE, this);
		gameBoardGrid.setFocusable(true);
		gameBoardGrid.setPreferredSize(new Dimension(WORD_GRID_COMP_SIZE, WORD_GRID_COMP_SIZE));
		add(gameBoardGrid, BorderLayout.CENTER);
		
		// Next build the side component (houses buttons and timer)
		FiverSideComponent sideComp = new FiverSideComponent(this, 
				SIDE_COMPONENT_SIZE_WTH, BUTTON_COMPONENT_SIZE_HGT, FiverGameBoardComponent.FRAME_BUFFER_PX);
		sideComp.setFocusable(true);
		sideComp.setPreferredSize(new Dimension(SIDE_COMPONENT_SIZE_WTH, BUTTON_COMPONENT_SIZE_HGT));
		add(sideComp, BorderLayout.EAST);
		
		// Add the logo
		ImageIcon logoImg = new ImageIcon("logosmall.png");
		JLabel logoComp = new JLabel(logoImg);
		logoComp.setPreferredSize(new Dimension(LOGO_COMPONENT_SIZE_WTH, LOGO_COMPONENT_SIZE_HGT));
		add(logoComp, BorderLayout.NORTH);
		
		pack();
		setVisible(true);		
	}
	
	
	/* -------------------------------------------------------------------------
	 * --                            PRIVATE METHODS                          --
	 * -------------------------------------------------------------------------
	 */
	

}
