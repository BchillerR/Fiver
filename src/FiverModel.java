import java.util.HashSet;
import java.util.Iterator;

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
	
	// Stores the current state of the game board
	public volatile String[] currGameBoard;
	
	// Stores the solution to the puzzle
	public volatile HashSet<String> gameBoardSolution;
	

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
	 * @param initialGameBoard the strings to use
	 */
	public FiverModel(String[] initialGameBoard)
	{
		// Record the solution
		recordSolution(initialGameBoard);
		
		// Reference to active game board 
		currGameBoard = initialGameBoard;
	}
	
	
	/**
	 * Used to set-up a new game board within a previously constructed model
	 * @param initialGameBoard the strings to use
	 */
	public void newGameModel(String[] initialGameBoard)
	{
		// Record the solution
		recordSolution(initialGameBoard);
		
		// Reference to active game board 
		currGameBoard = initialGameBoard;
	}
	
	
	/**
	 * Helper function for a creating a record of the solution
	 * @param initialGameBoard
	 */
	public void recordSolution(String[] initialGameBoard)
	{
		// Perform a deep copy of the solution by allocating a new
		// array and copying strings over
		gameBoardSolution = new HashSet<String>();
		
		for (int i = 0; i < initialGameBoard.length; i++)
		{
			// Strings in Java are immutable, so the below
			// method of copying is productive
			gameBoardSolution.add(initialGameBoard[i]);
		}
	}
	
	
	/**
	 * Sets the current gameboard to the solution
	 */
	public void setCurrToSolution()
	{
		Iterator<String> it = gameBoardSolution.iterator();
		
		// Copy elements from hashset to the array that's used
		// to store what the current state of the board is
		int i = 0;
		while (it.hasNext())
		{
			currGameBoard[i] = it.next();
			i++;
		}
	}
	
	
	/* -------------------------------------------------------------------------
	 * --                            PRIVATE METHODS                          --
	 * -------------------------------------------------------------------------
	 */	

}
