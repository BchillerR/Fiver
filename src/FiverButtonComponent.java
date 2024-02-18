import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;

/**
 * Class for the button panel
 */
public class FiverButtonComponent extends JComponent {

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
	

	/* -------------------------------------------------------------------------
	 * --                            PUBLIC METHODS                           --
	 * -------------------------------------------------------------------------
	 */
	
	/**
	 * Constructs the class
	 */
	public FiverButtonComponent(FiverView gameView)
	{
		// Store reference to view
		this.gameView = gameView;
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.NONE;
		
		c.gridx = 0;
		c.gridy = 0;		
		JButton newGameButton = new JButton("New Game");
		add(newGameButton, c);
		
		c.gridx = 0;
		c.gridy = 1;	
		JButton revealButton = new JButton("Reveal");
		add(revealButton, c);
		
		c.gridx = 0;
		c.gridy = 2;	
		JButton shuffleButton = new JButton("Shuffle");
		add(shuffleButton, c);
		
		c.gridx = 0;
		c.gridy = 3;
		FiverViewTimerComp gameTimerComp = new FiverViewTimerComp();
		add(gameTimerComp, c);
		
		// Next step is to draw the timer text. Going to create a new borderlayout
		// component to pack everything in. Buttons will go in center as a GBL.
		// Timer will go in south directly.
	}
	
	
	/* -------------------------------------------------------------------------
	 * --                            PRIVATE METHODS                          --
	 * -------------------------------------------------------------------------
	 */	
	
	private void genButtons()
	{
	
	}
	
	
	/* -------------------------------------------------------------------------
	 * --                             INNER CLASSES                           --
	 * -------------------------------------------------------------------------
	 */	
	
	/**
	 * Inner class for displaying a timer at the bottom of the button panel
	 */
	private class FiverViewTimerComp extends JComponent
	{
		/* -------------------------------------------------------------------------
		 * --                                 TYPES                               --
		 * -------------------------------------------------------------------------
		 */	
		
		
		/* -------------------------------------------------------------------------
		 * --                            PUBLIC FIELDS                            --
		 * -------------------------------------------------------------------------
		 */
		
		/**
		 * Constructs the class
		 */
		public FiverViewTimerComp()
		{
			
		}
		
		
		/* -------------------------------------------------------------------------
		 * --                            PRIVATE FIELDS                           --
		 * -------------------------------------------------------------------------
		 */
		
		
		/* -------------------------------------------------------------------------
		 * --                            PUBLIC METHODS                           --
		 * -------------------------------------------------------------------------
		 */
		
		public void paintComponent(Graphics g)
		{
			Graphics2D g2 = (Graphics2D)g;
		}
		
		
		/* -------------------------------------------------------------------------
		 * --                            PRIVATE METHODS                          --
		 * -------------------------------------------------------------------------
		 */			
		
	}
	
}
