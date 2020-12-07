/**

Description:
	This class creates the panel interface that will serve as the board for the game,
	and the black and white pieces.
*/

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.Icon;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class OthelloFrame extends JFrame
{
	private static final int WIDTH = 600;
	private static final int HEIGHT = 600;


	private JTextField message_field, turn_field;
	private JTextField dispWhiteScore;
	private JTextField dispBlackScore;
	private JLabel turnLabel, whiteLabel, blackLabel;
	private JPanel rightPanel, bottomPanel;
	private JPanel centerPanel;
	private playButton [][]othelloBoardSquareButton;
	private JButton newGameButton;
	private JButton passButton;
	private Othello othello;

	ImageIcon blackPiece, whitePiece, emptyPiece;

	//listener for newGame button
	private class newGameButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int i,j;
			for (i = 0; i < 8; i++)
			{
				for (j = 0; j < 8; j++)
				{
					othelloBoardSquareButton[i][j].setIcon(emptyPiece);
				}
			}
			othelloBoardSquareButton[3][3].setIcon(whitePiece);
			othelloBoardSquareButton[3][4].setIcon(blackPiece);
			othelloBoardSquareButton[4][3].setIcon(blackPiece);
			othelloBoardSquareButton[4][4].setIcon(whitePiece);

			othello.newGame();
			dispWhiteScore.setText("2");
			dispBlackScore.setText("2");
			message_field.setText("");
			turn_field.setText(othello.getWhoseTurn());
		}
	}
	
	//listener for the pass button
	private class passButtonListener implements ActionListener
	{
	    	public void actionPerformed(ActionEvent e)
    		{
   			message_field.setText(othello.getWhoseTurn() + " passes.");
   			othello.pass();
			turn_field.setText(othello.getWhoseTurn());
	    	}
	}

	//listener for the buttons on the board, activates when a play is made on the board
	private class othelloBoardSquareListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int i,j;
			String boardPiece = "";

			playButton button = (playButton)e.getSource();

			if (othello.playAPiece(button.row,button.column))
			{

				for (i = 0; i < 8; i++)
				{
					for (j = 0; j < 8; j++)
					{
						boardPiece = (String)othello.pieceAt(i,j);
						if (boardPiece.equals("Black"))
							othelloBoardSquareButton[i][j].setIcon(blackPiece);
						else if (boardPiece.equals("White"))
							othelloBoardSquareButton[i][j].setIcon(whitePiece);
						else
							othelloBoardSquareButton[i][j].setIcon(emptyPiece);
					}
				}
				message_field.setText("");
				turn_field.setText(othello.getWhoseTurn());
				dispWhiteScore.setText(othello.whitePieces()+"");
				dispBlackScore.setText(othello.blackPieces()+"");
				if (!othello.hasValidMove())
				{
					message_field.setText("No valid moves. " + othello.getWhoseTurn() + " passes.");
					othello.pass();
					turn_field.setText(othello.getWhoseTurn());
					
					if (!othello.hasValidMove())
					{
						message_field.setText("No more valid moves.  Game over!");
					}
				}
			}
			else
				message_field.setText("Invalid move");
		}
	}

	//creates a button object that carries row and column information
	private class playButton extends JButton
	{
		  private int row =0;
		  private int column = 0;
		  public playButton (int row, int column)
		  {
		    super();
		    this.row = row;
		    this.column = column;
		  }
	}

	//this will create the 8x8 board thay will be used to play the game.  Each board piece is a button object
	//that contains the row and column for each button, and is either black or contain a black or white circle
	private void initBoard()
	{
		int i, j;

		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(8, 8));
		centerPanel.setBackground(Color.BLUE);
		othelloBoardSquareButton = new playButton[8][8];

		for (i = 0; i < 8; i++)
		{
			for (j = 0; j < 8; j++)
			{
				othelloBoardSquareButton[i][j] = new playButton(i,j);
				othelloBoardSquareButton[i][j].setIcon(emptyPiece);
				centerPanel.add(othelloBoardSquareButton[i][j]);
				othelloBoardSquareButton[i][j].addActionListener(new othelloBoardSquareListener());
			}
		}
		othelloBoardSquareButton[3][3].setIcon(whitePiece);
		othelloBoardSquareButton[3][4].setIcon(blackPiece);
		othelloBoardSquareButton[4][3].setIcon(blackPiece);
		othelloBoardSquareButton[4][4].setIcon(whitePiece);
	}

	//this builds the whole frame for the game othello that contains the board game, buttons to start a new game
	//or for a player to pass his/her turn.  Also has textboxes to show whos turn it is to play and the score
	//of the players
	public OthelloFrame()
	{
		super("Othello");

		othello = new Othello();
		//images that will be loaded on the buttons to show playing pieces
		blackPiece = new ImageIcon("blackPieceImage.jpg");
		whitePiece = new ImageIcon("whitePieceImage.jpg");
		emptyPiece = new ImageIcon("");
		
		//sets up the main frame of the game
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new BorderLayout(20, 20));
		
		//calls class to create the playing board
		initBoard();

		//sets up space between board and the buttons on the right
		JPanel tempPanel = new JPanel();
		tempPanel.add(Box.createRigidArea(new Dimension(2,2)));
		this.add(tempPanel, BorderLayout.WEST);
		this.add(centerPanel, BorderLayout.CENTER);

		//sets up the panel that contains the buttons and textboxes and added to the right of the frame
		rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		passButton = new JButton("Pass");
		passButton.addActionListener(new passButtonListener());
		passButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		newGameButton = new JButton("New Game");
		newGameButton.addActionListener(new newGameButtonListener());
		newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		rightPanel.add(Box.createRigidArea(new Dimension(20, 20)));
		rightPanel.add(passButton);
		rightPanel.add(Box.createRigidArea(new Dimension(20, 40)));
		rightPanel.add(newGameButton);
		
		turn_field = new JTextField("White", 3);
		turn_field.setEditable(false);
		turnLabel = new JLabel("Turn: ");
		rightPanel.add(Box.createRigidArea(new Dimension(10, 90)));
		rightPanel.add(turnLabel);
		rightPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		rightPanel.add(turn_field);
		
		dispBlackScore = new JTextField("2", 2);
		dispBlackScore.setEditable(false);
		blackLabel = new JLabel("Black: ");
		rightPanel.add(Box.createRigidArea(new Dimension(10, 90)));
		rightPanel.add(blackLabel);
		rightPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		rightPanel.add(dispBlackScore);
		
		dispWhiteScore = new JTextField("2", 2);
		dispWhiteScore.setEditable(false);
		whiteLabel = new JLabel("White: ");
		rightPanel.add(Box.createRigidArea(new Dimension(10, 90)));
		rightPanel.add(whiteLabel);
		rightPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		rightPanel.add(dispWhiteScore);
		
		this.add(rightPanel, BorderLayout.EAST);

		bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());

		
		message_field = new JTextField("", 40);
		message_field.setEditable(false);

		bottomPanel.add(message_field);

		this.add(bottomPanel, BorderLayout.PAGE_END);
	}
}
