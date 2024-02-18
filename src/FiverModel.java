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
		// Record the solution
		recordSolution(initialGameBoard);
		
		// Reference to active game board 
		currGameBoard = initialGameBoard;
	}
	
	/**
	 * Helper function for a creating a deep copy of the solution
	 * @param initialGameBoard
	 */
	public void recordSolution(String[] initialGameBoard)
	{
		// Perform a deep copy of the solution by allocating a new
		// array and copying strings over
		gameBoardSolution = new String[initialGameBoard.length];
		
		for (int i = 0; i < gameBoardSolution.length; i++)
		{
			// Strings in Java are immutable, so the below
			// method of copying is productive
			gameBoardSolution[i] = gameBoardSolution[i];
		}
	}
	
	
	/* -------------------------------------------------------------------------
	 * --                            PRIVATE METHODS                          --
	 * -------------------------------------------------------------------------
	 */	

}
