import java.util.HashSet;

/**
 * Controller class
 */
public class FiverController {
	
	/* -------------------------------------------------------------------------
	 * --                                 TYPES                               --
	 * -------------------------------------------------------------------------
	 */	
	
	/* For indicating whether a col should be rotated up or down */
	public enum RotateDirection
	{
		UP,
		DOWN
	}
	
	/* Game state machine */
	static public enum GameStateMachine
	{
		INITIALIZE,
		GAME_ACTIVE,
		GAME_SOLVED,
		GAME_REVEAL,
		SET_UP_NEW_GAME		
	}
	
	
	/* -------------------------------------------------------------------------
	 * --                            PUBLIC FIELDS                            --
	 * -------------------------------------------------------------------------
	 */	
	
	// Size of the game board
	public final static int BOARD_SIZE = 5;

	// Reference to the game's model
	public FiverModel gameModel;
	
	// Indicates whether current puzzle is solved
	public volatile boolean is_Solved;
	
	// Indicates whether program should start a new game
	public volatile boolean startNewGame = false;
	

	/* -------------------------------------------------------------------------
	 * --                            PRIVATE FIELDS                           --
	 * -------------------------------------------------------------------------
	 */

	/* -------------------------------------------------------------------------
	 * --                            PUBLIC METHODS                           --
	 * -------------------------------------------------------------------------
	 */
	
	/**
	 * Constructs the app
	 */
	public FiverController()
	{
		// Do nothing
	}
	

	/**
	 * Rotates the col indicated by curr_Col in the direction
	 * specified by upOrDown
	 * @param currCol the col to rotate
	 * @param upOrDown direction to rotate curr_Col
	 */
	public void rotateRow(int currCol, RotateDirection upOrDown)
	{
		/* To simplify the process of rotating, we're going to build a string
		 * that represents what the column should look like after it's rotated
		 */
		String rotatedStr = "";

		for (int row = 0; row < BOARD_SIZE; row++)
		{
			String currStr;
			
			switch( upOrDown )
			{
				case UP:
					// Grab from one row up relative to the iterator
					currStr = gameModel.currGameBoard[( ((row + BOARD_SIZE) - 1) % BOARD_SIZE )];
					
					break;
	
				case DOWN:
				default: 
					// Grab from one row down relative to the iterator
					currStr = gameModel.currGameBoard[( (row + 1) % BOARD_SIZE )];
					
					break;
			}
			
			// Append to the growing, rotated string
			rotatedStr += currStr.charAt(currCol);
		}
		
		/* Iterate over each string in the gameboard. Update each string at index
		 * 'currCol' with the corresponding char at the same index in rotatedStr
		 */
		for (int row = 0; row < BOARD_SIZE; row++)
		{
			StringBuilder updatedStr = new StringBuilder(gameModel.currGameBoard[row]);
			updatedStr.setCharAt(currCol, rotatedStr.charAt(row));
			gameModel.currGameBoard[row] = updatedStr.toString();
		}
	}
	
	
	/**
	 * Shuffle current gameboard
	 */
	public void shuffleGameboard()
	{
		// Iterate over every column
		for (int i = 0; i < BOARD_SIZE; i++)
		{
			// Generate a random value to rotate the current column by
			int rotateAmount = (int)Math.floor(Math.random() * BOARD_SIZE);
			
			// Rotate the current column by the randomly generated amount
			for (int rotateCtr = 0; rotateCtr < rotateAmount; rotateCtr++)
			{
				rotateRow(i, RotateDirection.UP);
			}
		}
	}
	
	
	/**
	 * Checks whether puzzle is solved
	 */
	public void checkForSolved()
	{
		boolean solved = false;
		
		String[] currGameBoard = gameModel.currGameBoard;
		HashSet<String> gameBoardSolution = gameModel.gameBoardSolution;
		
		// Number of matches
		int checkCtr = 0;
		
		// Check each string in current game board against solution
		for (int i = 0; i < currGameBoard.length; i++)
		{
			if (gameBoardSolution.contains(currGameBoard[i]))
			{
				/* Note: probability wise, one hit should be enough.
				 *       We're going to check every row just in case though,
				 *       in the rare event the letter distribution/ redundancy 
				 *       permits one word to be solved while the rest haven't
				 *       been.
				 */
				checkCtr++;
			}
			else
			{
				break;
			}
		}
		
		// Puzzle is solved when all rows hit
		if (BOARD_SIZE == checkCtr)
		{
			solved = true;
		}
		
		is_Solved = solved;
	}
	

	/**
	 * Setter function to give controller a reference to the game's model
	 * @param gameModel the game's model
	 */
	public void setGameModel(FiverModel gameModel)
	{
		this.gameModel = gameModel;
	}
	

	public static void main(String[] args) {
		
		// Instantiate the controller
		FiverController gameController = new FiverController();
		
		// Generate words for initial round
		Words wordMachine = new Words();
		String[] gameWords = wordMachine.getUniqueRandWords(FiverController.BOARD_SIZE);

		// Generate model
		FiverModel gameModel = new FiverModel(gameWords);

		// Give controller reference to model
		gameController.setGameModel(gameModel);
		
		// Scramble board
		gameController.shuffleGameboard();
		
		// Mark puzzle as not solved
		gameController.is_Solved = false;
		
		// Generate view
		FiverView gameView = new FiverView(gameController);
		
		// Set entry for state machine
		GameStateMachine currState = GameStateMachine.GAME_ACTIVE;
		
		// State machine is active until player closes out of application
		for (;;)
		{
			switch (currState)
			{
				// Where the application cycles when a game is active
				case GAME_ACTIVE:
					
					// Check for solved
					if (false != gameController.is_Solved)
					{
						currState = GameStateMachine.GAME_SOLVED;
					}
					// Check if there's a signal to start a new game
					else if (false != gameController.startNewGame)
					{
						currState = GameStateMachine.SET_UP_NEW_GAME;
					}
					
					break;
				
				// Player solved the game
				case GAME_SOLVED:
					
					// Wait for player to click start game
					if (false != gameController.startNewGame)
					{
						currState = GameStateMachine.SET_UP_NEW_GAME;
					}
					
					break;
				
				// Player selected reveal
				case GAME_REVEAL:
					
					
					break;
				
				// Set up a new game
				case SET_UP_NEW_GAME:
										
					// Clear state transition signals
					gameController.startNewGame = false;
					
					// Generate new list of words and update model
					gameWords = wordMachine.getUniqueRandWords(FiverController.BOARD_SIZE);
					gameModel.newGameModel(gameWords);
					
					// Scramble board
					gameController.shuffleGameboard();
					
					// Mark puzzle as not solved
					gameController.is_Solved = false;
					
					// Go back to active state
					currState = GameStateMachine.GAME_ACTIVE;
					
					// Repaint view
					gameView.repaint();
					
					break;
				
				default: 
					// Do nothing
					
					break;
			}
			
			// Pace the main thread
			try 
			{
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	/* -------------------------------------------------------------------------
	 * --                            PRIVATE METHODS                          --
	 * -------------------------------------------------------------------------
	 */

}
