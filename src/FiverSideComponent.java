import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

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
	
	// Timer component size expressed as a percentage of the
	// side component's preferred size
	private static final double TIMER_COMP_SIZE_PCT = 0.75;
	

	/* -------------------------------------------------------------------------
	 * --                            PUBLIC METHODS                           --
	 * -------------------------------------------------------------------------
	 */
	
	/**
	 * Constructs the class
	 * @param gameView reference to the game's view
	 * @param prefWidth the width allocated to the sidebar
	 * @param prefHeight the height allocated to the side bar
	 * @param wordGridPadding the padding around the word grid (needed for alignment purposes)
	 */
	public FiverSideComponent(FiverView gameView, int prefWidth, 
			int prefHeight, int wordGridPadding)
	{
		// Store reference to view
		this.gameView = gameView;
		
		setLayout(new BorderLayout());
		
		// Going to build the side panel from top to bottom
		// Start with padding. Buttons look better top aligned with word grid
		JLabel padding = new JLabel();
		padding.setPreferredSize(new Dimension(prefWidth, wordGridPadding));
		add(padding, BorderLayout.NORTH);
		
		// Calculate how much height is left for the actual side bar content
		int remHeight = prefHeight - wordGridPadding;
		
		// Next build the buttons
		FiverViewButtonComp buttonComp = new FiverViewButtonComp();
		int buttonSizeCalc = (int)Math.ceil(remHeight * (1-TIMER_COMP_SIZE_PCT));
		buttonComp.setPreferredSize(new Dimension(prefWidth, buttonSizeCalc));
		add(buttonComp, BorderLayout.CENTER);
		
		// Next build the timer
		FiverViewTimerComp timerComp = new FiverViewTimerComp();
		int timerSize = remHeight - buttonSizeCalc;
		timerComp.setPreferredSize(new Dimension(prefWidth, timerSize));
		add(timerComp, BorderLayout.SOUTH);		
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
			
			// Buttons will span entire width of the component
			c.fill = GridBagConstraints.HORIZONTAL;
			
			// Top align buttons
			c.anchor = GridBagConstraints.NORTH;
			c.weighty = 1.0;
			
			// Construct new game button
			c.gridx = 0;
			c.gridy = 0;
			JButton newGameButton = new JButton("New Game");
			newGameButton.addActionListener(new ActionListener()
				{
					// Signal to the back-end to start a new game
					public void actionPerformed(ActionEvent e)
					{
						gameView.gameController.startNewGame = true;
					}
				});
			styleButton(newGameButton);
			add(newGameButton, c);
			
			// Construct reveal button
			c.gridx = 0;
			c.gridy = 1;	
			JButton revealButton = new JButton("Reveal");
			styleButton(revealButton);
			add(revealButton, c);
			
			// Construct shuffle button
			c.gridx = 0;
			c.gridy = 2;	
			JButton shuffleButton = new JButton("Shuffle");
			shuffleButton.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e)
					{
						if (false == gameView.gameController.is_Solved)
						{
							gameView.gameController.shuffleGameboard();
							gameView.gameBoardGrid.repaint();
						}
					}
				});
			styleButton(shuffleButton);
			add(shuffleButton, c);
		}
		
		
		/* -------------------------------------------------------------------------
		 * --                            PRIVATE FIELDS                           --
		 * -------------------------------------------------------------------------
		 */
		
		/**
		 * Style buttons to make them fit the aesthetic
		 * @param b the button to style
		 */
		private void styleButton(JButton b)
		{
			// Make button black and white
			b.setForeground(Color.BLACK);
			b.setBackground(Color.WHITE);
			
			// Remove the focus feature to make buttons more minimalistic
			b.setFocusPainted(false);
		}
		
		
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
