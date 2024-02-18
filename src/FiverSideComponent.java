import java.awt.BorderLayout;
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
public class FiverSideComponent extends JComponent {

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
	public FiverSideComponent(FiverView gameView)
	{
		// Store reference to view
		this.gameView = gameView;
		
		setLayout(new BorderLayout());
		
		FiverViewButtonComp buttonComp = new FiverViewButtonComp();
		buttonComp.setFocusable(true);
		add(buttonComp, BorderLayout.NORTH);
	}
	
	
	/* -------------------------------------------------------------------------
	 * --                            PRIVATE METHODS                          --
	 * -------------------------------------------------------------------------
	 */	
		
	
	/* -------------------------------------------------------------------------
	 * --                             INNER CLASSES                           --
	 * -------------------------------------------------------------------------
	 */	
	
	/**
	 * Inner class for displaying buttons
	 */
	private class FiverViewButtonComp extends JComponent
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
		public FiverViewButtonComp()
		{
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
		}
		
		
		/* -------------------------------------------------------------------------
		 * --                            PRIVATE FIELDS                           --
		 * -------------------------------------------------------------------------
		 */
		
		
		/* -------------------------------------------------------------------------
		 * --                            PUBLIC METHODS                           --
		 * -------------------------------------------------------------------------
		 */
		
		
		/* -------------------------------------------------------------------------
		 * --                            PRIVATE METHODS                          --
		 * -------------------------------------------------------------------------
		 */			
		
	}
	
	
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
