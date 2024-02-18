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
	 * Setter function to give controller a reference to the game's model
	 * @param gameModel the game's model
	 */
	public void setGameModel(FiverModel gameModel)
	{
		this.gameModel = gameModel;
	}
	

	public static void main(String[] args) {
		/* Generate controller */
		FiverController gameController = new FiverController();

		/* Generate words for initial round */
		Words wordMachine = new Words();
		String[] gameSolution = wordMachine.getUniqueRandWords(gameController.BOARD_SIZE);

		/* Generate model */
		FiverModel gameModel = new FiverModel(gameSolution);

		/* Give controller reference to model */
		gameController.setGameModel(gameModel);

		/* Generate view */
		FiverView gameView = new FiverView(gameController, gameModel.currGameBoard);
	}
	
	
	/* -------------------------------------------------------------------------
	 * --                            PRIVATE METHODS                          --
	 * -------------------------------------------------------------------------
	 */

}
