/**
 * Model class
 */
public class FiverModel {
	
	/* -------------------------------------------------------------------------
	 * --                                 TYPES                               --
	 * -------------------------------------------------------------------------
	 */	
	
	
	/* -------------------------------------------------------------------------
	 * --                            PUBLIC FIELDS                            --
	 * -------------------------------------------------------------------------
	 */	
	
	// Stores the current state of the gameboard
	public String[] currGameBoard;
	
	// Stores the solution to the puzzle
	public String[] gameBoardSolution;
	

	/* -------------------------------------------------------------------------
	 * --                            PRIVATE FIELDS                           --
	 * -------------------------------------------------------------------------
	 */
	

	/* -------------------------------------------------------------------------
	 * --                            PUBLIC METHODS                           --
	 * -------------------------------------------------------------------------
	 */
	
	/**
	 * Constructs a game based on the provided board size
	 * @param boardSize Size of the game grid
	 */
	public FiverModel(String[] initialGameBoard)
	{
		gameBoardSolution = initialGameBoard;
		currGameBoard = initialGameBoard;
	}	
	
	
	/* -------------------------------------------------------------------------
	 * --                            PRIVATE METHODS                          --
	 * -------------------------------------------------------------------------
	 */	

}
