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
	
	/* Size of the game board */
	public final static int BOARD_SIZE = 5;

	/* Reference to the game's model */
	public FiverModel gameModel;
	

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
	 * Setter function to give controller a reference to the game's model
	 * @param gameModel the game's model
	 */
	public void setGameModel(FiverModel gameModel)
	{
		this.gameModel = gameModel;
	}
	

	public static void main(String[] args) {
		
		// Prime the applicatin's top level state machine
		GameStateMachine currState = GameStateMachine.INITIALIZE;
		
		switch (currState)
		{
			// Initialize the game
			case INITIALIZE:			
				// Generate controller
				FiverController gameController = new FiverController();
		
				// Generate words for initial round
				Words wordMachine = new Words();
				String[] gameWords = wordMachine.getUniqueRandWords(gameController.BOARD_SIZE);
		
				// Generate model
				FiverModel gameModel = new FiverModel(gameWords);
		
				// Give controller reference to model
				gameController.setGameModel(gameModel);
				
				// Scramble board
				gameController.shuffleGameboard();
				
				// Generate view
				FiverView gameView = new FiverView(gameController, gameModel.currGameBoard);
				
				// Initialization complete
				currState = GameStateMachine.GAME_ACTIVE;
				
				break;
			
			// Where the application cycles when a game is active
			case GAME_ACTIVE:
				
				
				break;
			
			// Player solved the game
			case GAME_SOLVED:
				
				
				break;
			
			// Player selected reveal
			case GAME_REVEAL:
				
				
				break;
			
			// Set up a new game
			case SET_UP_NEW_GAME:
				
				break;
			
			default: 
				// Do nothing
				
				break;
		}
	}
	
	
	/* -------------------------------------------------------------------------
	 * --                            PRIVATE METHODS                          --
	 * -------------------------------------------------------------------------
	 */

}
