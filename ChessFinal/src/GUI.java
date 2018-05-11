//created by nick bulinski
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class GUI extends JFrame
{
	private int turn; // this variable controls whether or not it is player1 or player2 depending on if it is set to 1 or 2 respectively
	private int step; // controls whether or not the player is selecting a piece or the player is moving a piece
	private ChessBoard board; // the board class that keeps track of the positions of each piece
	private HumanPlayer player1; // this is the player class that contains all of the pieces that belong to player1 and methods that control the pieces
	private HumanPlayer player2; // same as player1's player class only with pieces that belong to player2
	private String pieceSelected; // this is the variable that keeps track of piece that was selected
	private Button[][] buttonsData; // this 2D array is the array of the button class that controls all of the text and returns the row and column of the buttons position
	private JLabel[] info; // this is the info array that tells the player who's turn it is as well as telling the players how many pieces the players have left
	public static void main(String[] args) 
	{
		new GUI().Driver(); // main driver
	}
	private void Driver() // this is where the gui is run
	{
		JFrame frame = new GUI();
		frame.setTitle("Chess Game");
		frame.setSize(1000, 700);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	/*this is the constructor for the gui it contains the graphics as well as the listiner's
	 * this is where all of the player, board, and button classes are created and the turn and step variables are initialized
	 * 
	 */
	GUI()
	{
		// 37-43 are the lines that initialized all of the variables
		board = new ChessBoard();
		player1 = new HumanPlayer(board.getSize(), 1);
		player2 = new HumanPlayer(board.getSize(), 2);
		this.Pieces(board, player1, player2);
		buttonsData = new Button[board.getSize()][board.getSize()];
		turn = 1;
		step = 1;
		//the majority of this code is just creating the panels and making it look pretty
		JPanel mainPanel = new JPanel(); // panel the holds both the buttons and the text panels
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(new LineBorder(Color.BLACK, 2));
		JPanel buttonPanel = new JPanel(); // panel that contains the buttons
		buttonPanel.setLayout(new GridLayout(8,8));
		JPanel textPanel = new JPanel(); // panel where the info is continued
		setUpInfo(); // sets up the info for the players to look at
		textPanel.setLayout(new GridLayout(3,1));
		textPanel.setBorder(new TitledBorder("Game Infomation"));
		textPanel.setBackground(Color.CYAN);
		int lineWidth = 2;
		setUpButtons(lineWidth); // set up the buttons
		this.enableButtonsForValidPieceSelect(player1); // sets the buttons for player one to select enabled for the buttons that are located where the players pieces are
		// this for loop is used to add the buttons to the button panel
		for(int i = 0; i<board.getSize(); i++)
		{
			for(int j = 0; j<board.getSize(); j++)
			{
				buttonPanel.add(buttonsData[i][j].getButton());
			}
		}
		// this for loop is for adding the info to the text panel
		for(int i = 0; i<info.length; i++)
		{
			textPanel.add(info[i]);
		}
		// this is where the text panel and button panel are added to the main panel
		mainPanel.add(buttonPanel, BorderLayout.CENTER);
		mainPanel.add(textPanel, BorderLayout.WEST);
		// adds the main panel to the frame
		add(mainPanel);
		// this for loop is used to add action listeners to all of the buttons
		for(int i = 0; i<buttonsData.length; i++)
		{
			for(int j = 0; j<buttonsData.length; j++)
			{
				// because the listener will only take final variables (so not i and j) i had to make k and n final and equal to i and j respectively
				final int k = i;
				final int n = j;
				// this is where the action listeners are added to the buttons
				buttonsData[i][j].getButton().addActionListener(new ActionListener() 
				{
					// the action performed is the same for all of the buttons however depending on the turn and step different actions will triger
			        public void actionPerformed(ActionEvent e) 
			        {
			        	// if it is player ones turn this if statement will be evaluated to true
			        	if(turn==player1.getOrder())
			        	{
			        		actionsOnTurn(player1, player2, board, k, n); // this is where the logic is cared out on player ones turn
			        		if(checkmate(player1, player2, player2.getX("_K2_"), player2.getY("_K2_"), board))
			        		{
			        			endGame(player1); // if player one gets a checkmate this will come up
			        		}
			        	}
			        	// same as the first statement only for player two instead of player one
			        	else if(turn==player2.getOrder())
			        	{
			        		actionsOnTurn(player2, player1, board, k, n);
			        		if(checkmate(player2, player1, player1.getX("_K1_"), player1.getY("_K1_"), board))
			        		{
			        			endGame(player2);
			        		}
			        	}
			        }
			    }
				);
			}
		}
	}
	/* this method is what makes the entire program work
	 * with out it the listener would do nothing
	 */
	/**
	* this method will run the logic for the player passed in depending on whatever step it currently is
	* @param player
	* @param otherPlayer
 	* @param board
 	* @param buttonRow
 	* @param buttonCollom
 	*/
	private void actionsOnTurn(Player player, Player otherPlayer, Board board, int buttonRow, int buttonCollom)
	{
		if(step==1) // this means that the player has just clicked a button that contains a piece that is valid to the player passes in
		{
			this.enableButtonsForValidMove(player, board, buttonRow, buttonCollom); // this enables the buttons that would be a valid move
			this.pieceSelected = player.getID(buttonCollom, buttonRow); // sets the piece selected variable that will be needed for later
			this.step = 2; // this makes it so the next button click will run step 2
		}
		else if(step==2)
		{
			// this determines whether or not the move is an attack move or just a standard move
			if(isAttack(buttonRow, buttonCollom, otherPlayer, board)==true)
			{
				takePiece(player, otherPlayer, board, buttonRow, buttonCollom, this.pieceSelected);
			}
			else
			{
				movePiece(player, board, buttonRow, buttonCollom, this.pieceSelected);
			}
			// sets the turn to the other players turn and sets the step equal to one
			this.step = 1;
			this.turn = otherPlayer.getOrder();
			// the text on the buttons is now updated and and the buttons that can be selected for other player to move are enabled as well as the info geting updated
			this.updateBourd(board);
			this.enableButtonsForValidPieceSelect(otherPlayer);
			updateInfo();
		}
	}
	/**
	 * this the method that runs the end game frame; the end game frame tells the player who has acheved victory and asked them if they want to play agine; yes will reset the program and no will end the program
	 * @param player
	 */
	// i use this in the action listener whenever there is a checkmate
	private void endGame(Player player)
	{
		JFrame frame = createEndGameFrame(player);
		frame.setSize(200, 100);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	/**
	 * this creates a frame with 2 JPanels, 2 JLabels, and 2 JButtons; adds 2 action listeners, one that will reset the board and one that will end the program
	 * @param player
	 * @return JFrame
	 */
	// only used in the endGame function
	private JFrame createEndGameFrame(Player player)
	{
		// creates the frame
		final JFrame frame = new JFrame();
		// creates the two JPanels
		JPanel yesNo = new JPanel(new GridLayout(1,2));
		JPanel victory = new JPanel(new BorderLayout()); // victory is the main panel for the endGame menu
		victory.setBackground(Color.WHITE);
		victory.setBorder(new LineBorder(Color.BLACK, 1));
		//creates the two JLables
		JLabel win = new JLabel("Player "+player.getOrder()+" wins");
		JLabel choice = new JLabel("Do you want to play again?");
		//creates the two JButtons
		JButton yes = new JButton("Yes");
		yes.setBackground(Color.CYAN);
		yes.setBorder(new LineBorder(Color.BLACK, 1));
		JButton no = new JButton("No");
		no.setBackground(Color.MAGENTA);
		no.setBorder(new LineBorder(Color.BLACK, 1));
		// adds the yes and no button to the yesNo panel
		yesNo.add(yes);
		yesNo.add(no);
		// adds the two JLables win and choice as well as the yesNo panel containing the yes and know buttons
		victory.add(win, BorderLayout.NORTH);
		victory.add(choice, BorderLayout.CENTER);
		victory.add(yesNo, BorderLayout.SOUTH);
		// adds the main panel to the frame for the endGame menu
		frame.add(victory);
		// this is where the action listener for the yes button is added
		yes.addActionListener(new ActionListener()
		{
			// if yes is selected the action performed will reset all of the classes and variables to there original states and updates the text on the buttons
			// it also will update the info the dispose the frame allowing the player to start playing the game once more
	        public void actionPerformed(ActionEvent e) 
	        {
	        	board = new ChessBoard();
	    		player1 = new HumanPlayer(board.getSize(), 1);
	    		player2 = new HumanPlayer(board.getSize(), 2);
	    		Pieces(board, player1, player2);
	    		turn = 1;
	    		step = 1;
	    		updateBourd(board);
	    		enableButtonsForValidPieceSelect(player1);
	    		updateInfo();
	    		frame.dispose();
	        }
	    }
		);
		// this is where the action listener is added to the no button
		no.addActionListener(new ActionListener() 
		{
			// if no is selected then the program simply calls the System.exit(0) and end the program
	        public void actionPerformed(ActionEvent e) 
	        {
	        	System.exit(0);
	        }
	    }
		);
		// returns the completed JFrame
		return frame;
	}
	/**
	 * sets up the buttons with the initial text to be displayed, the alternating color of the board, and sets the boarder
	 * @param lineWidth
	 */
	// used in the constructor
	private void setUpButtons(int lineWidth)
	{
		// the nested for loop is so that the colors will alternate in the row and then switch the order on the next row
		for(int i = 0; i<board.getSize(); i++)
		{
			// this for loop go's through the spaces on that row alternating the colors with first green then red
			for(int j = 0; j<board.getSize(); j++)
			{
				buttonsData[i][j] = new Button(i,j);
				buttonsData[i][j].setText(board.getSpace(i, j));
				buttonsData[i][j].setColor(Color.BLACK);
				buttonsData[i][j].setBorders(Color.BLACK, lineWidth);
				j++; // this is how you get the green and red spaces alternating
				buttonsData[i][j] = new Button(i,j);
				buttonsData[i][j].setText(board.getSpace(i, j));
				buttonsData[i][j].setColor(Color.RED);
				buttonsData[i][j].setBorders(Color.BLACK, lineWidth);
			}
			i++; // skips to the next line to switch the order of the alternating colors this time red then green
			for(int j = 0; j<board.getSize(); j++)
			{
				buttonsData[i][j] = new Button(i,j);
				buttonsData[i][j].setText(board.getSpace(i, j));
				buttonsData[i][j].setColor(Color.RED);
				buttonsData[i][j].setBorders(Color.BLACK, lineWidth);
				j++;
				buttonsData[i][j] = new Button(i,j);
				buttonsData[i][j].setText(board.getSpace(i, j));
				buttonsData[i][j].setColor(Color.BLACK);
				buttonsData[i][j].setBorders(Color.BLACK, lineWidth);
			}
		}
	}
	/** this sets up the info to be displayed on the board because all of the hard coding
	 * 
	 */
	// called in the constructor
	private void setUpInfo()
	{
		JLabel[] tempInfo = {new JLabel("player"+turn+"s turn"), 
				new JLabel("player"+player1.getOrder()+" has "+player1.sizeOfList()+" pieces left"), 
				new JLabel("player"+player2.getOrder()+" has "+player2.sizeOfList()+" pieces left")};
		info = tempInfo;
		Font font = new Font("Serif", Font.BOLD, 20);
		info[0].setFont(font);
		info[1].setFont(font);
		info[2].setFont(font);
		
	}
	/** this updates all the info to be displayed
	 * 
	 */
	// called in the actionsOnTurn and in the createEndGamePanel methods
	private void updateInfo()
	{
		info[0].setText("player"+turn+"s turn");
		info[1].setText("player"+player1.getOrder()+" has "+player1.sizeOfList()+" pieces left");
		info[2].setText("player"+player2.getOrder()+" has "+player2.sizeOfList()+" pieces left");
	}
	/**
	 * this method will enable all of the buttons that have a piece on that space that belong to a player passed in
	 * @param playerTurn
	 */
	// used primarily in the constructor and in the actionsOnTurn method as well as being called in the action listener for the yes button in the createEndGameFrame
	private void enableButtonsForValidPieceSelect(Player playerTurn)
	{
		for(int i = 0; i<buttonsData.length; i++)
		{
			for(int j = 0; j<buttonsData.length; j++)
			{
				if(playerTurn.validID(board.getSpace(i, j)))
				{
					buttonsData[i][j].enableButton(); // if the ID is valid the button will be enabled
				}
				else
				{
					buttonsData[i][j].disableButton(); // if the ID is invalid the button will be disabled
				}
			}
		}
	}
	/**
	 *  this method enables all of the spaces that would be valid for the piece in question and disables all of the spaces that are not valid for the piece to move to
	 * @param playersTurn
	 * @param board
	 * @param row
	 * @param collom
	 */
	// called in the actionsOnTurn method in step 2
	private void enableButtonsForValidMove(Player playersTurn, Board board, int row, int collom)
	{
		// this loop searches through the board looking for a valid space to move the piece
		for(int i = 0; i<buttonsData.length; i++)
		{
			for(int j = 0; j<buttonsData.length; j++)
			{
				// if the space chosen is an valid space to move to this will enable the button
				if(playersTurn.moveValid(playersTurn.getID(collom, row), i, j, board)&&playersTurn.validID(board.getSpace(i, j))==false)
				{
					buttonsData[i][j].enableButton();
				}
				// if the space is chosen is an invalid space to move to this will disable the button
				else
				{
					buttonsData[i][j].disableButton();
				}
			}
		}
	}
	/**
	 * this method updates the all of the text displayed on the buttons
	 * @param board
	 */
	// used in the actionsOnTurn and the createEndGameFrame methods
	private void updateBourd(Board board)
	{
		// get the text data in the board class and sets the button text to the corisponding board text
		for(int i = 0; i<buttonsData.length; i++)
		{
			for(int j = 0; j<buttonsData.length; j++)
			{
				buttonsData[i][j].setText(board.getSpace(i, j));
			}
		}
	}
	/**
	 * places the pieces on the board at the row and collom specifed for each piece
	 * @param board
	 * @param player1
	 * @param player2
	 */
	// used in the GUI constructor as well as the action listener for the yes button
	private void Pieces(Board board, Player player1, Player player2)
	{
		board.placeArrayOfPiecesOnBoard(player1);
		board.placeArrayOfPiecesOnBoard(player2);
	}
	/**
	 * this checks to see if a move is an attack move or just a regular move
	 * @param newRow
	 * @param newCollom
	 * @param defendingPlayer
	 * @param board
	 * @return
	 */
	// used in the actionsOnTurn method during step 2
	private boolean isAttack(int newRow, int newCollom, Player defendingPlayer, Board board)
	{
		if(board.getSpace(newRow, newCollom)!=board.getEmptyValue())
		{
			if(defendingPlayer.validID(board.getSpace(newRow, newCollom))==true)
			{
				return true;
			}
		}
		return false;
	}
	/**
	 * moves the piece to a new position on the board
	 * @param player
	 * @param board
	 * @param newRow
	 * @param newCollom
	 * @param ID
	 */
	// used in the actionsOnTurn method during step 2 if not an attack move
	private void movePiece(Player player, Board board, int newRow, int newCollom, String ID)
	{
		board.ResetSpace(player.getX(ID), player.getY(ID));
		player.setX(ID, newRow);
		player.setY(ID, newCollom);
		board.setSpace(ID, newRow, newCollom);
	}
	/**
	 * takes on piece and move it to the position where the defending piece is at which point the defending piece is removed from there corisponding player class
	 * @param attakingPlayer
	 * @param defendingPlayer
	 * @param board
	 * @param newRow
	 * @param newCollom
	 * @param ID
	 */
	// used in the actionsOnTurn method if the move is an attack move
	private void takePiece(Player attakingPlayer, Player defendingPlayer, Board board, int newRow, int newCollom, String ID)
	{
		defendingPlayer.remove(board.getSpace(newRow, newCollom));
		board.ResetSpace(newRow, newCollom);
		board.ResetSpace(attakingPlayer.getX(ID), attakingPlayer.getY(ID));
		attakingPlayer.setX(ID, newRow);
		attakingPlayer.setY(ID, newCollom);
		board.setSpace(ID, newRow, newCollom);
	}
	/**
	 * checks to see if there is a checkmate; it will return true if there is a checkmate and false if there is not
	 * @param winingPlayer
	 * @param loseingPlayer
	 * @param kingsRow
	 * @param kingsCollom
	 * @param board
	 * @return boolean
	 */
	// used in the action listener for the buttons and it is one of the ways that you can end the game
	private boolean checkmate(Player winingPlayer, Player loseingPlayer, int kingsRow, int kingsCollom, Board board)
	{
		// it works by calling this nested if statment that first checks to see if the king si in check and then if true checks to see if the king can move out of check
		if(isInCheck(winingPlayer, kingsRow, kingsCollom, board)==true)
		{
			if(canMoveOutOfCheck(winingPlayer, loseingPlayer, kingsRow, kingsCollom, board)==false)
			{
				return true;
			}
		}
		return false;
	}
	/**
	 * method used to see if the king on the team that is not the player is in check; will return true if the king is in check and false if the king is not in check
	 * @param player
	 * @param kingsRow
	 * @param kingsCollom
	 * @param board
	 * @return boolean
	 */
	// used in the checkmate method
	private boolean isInCheck(Player player, int kingsRow, int kingsCollom, Board board)
	{
		for(int i = 0; i<player.sizeOfList(); i++)
		{
			if(player.moveValid(i, kingsRow, kingsCollom, board)==true)
			{
				return true;
			}
		}
		return false;
	}
	/**
	 * method that will check and see if the king can move out of check; will return true if the king can move out of harms way will return false if the king cannot escape
	 * @param winingPlayer
	 * @param loseingPlayer
	 * @param kingsRow
	 * @param kingsCollom
	 * @param board
	 * @return boolean
	 */
	// used in the checkmate method only
	private boolean canMoveOutOfCheck(Player winingPlayer, Player loseingPlayer,int kingsRow, int kingsCollom, Board board)
	{
		String ID = loseingPlayer.getID(kingsCollom, kingsRow); // this sets is the ID tag for the king
		int index = -1; // this is my way to set this method to return false at first
		// the for loop will check all of the spaces to see where the king can move
		for(int i = 0; i<board.getSize(); i++)
		{
			for(int j = 0; j<board.getSize(); j++)
			{
				// at this point we call the move valid method to see if the king can get to this space without putting himself in check
				if(loseingPlayer.moveValid(ID, i, j, board)==true&&board.getSpace(i, j).compareTo(board.getEmptyValue())==0)
				{
					// this is the loop that detarmans if the king can move out or not
					for(int k = 0; k<winingPlayer.sizeOfList(); k++)
					{
						// if the spot is a spot that one of the pieces can attack the loop will break and the process will start over one more
						if(winingPlayer.moveValid(k, i, j, board)==true)
						{
							index = -1;
							break;
						}
						// will continue to set the index = 0 unless there is a piece that can attack this position
						else if(winingPlayer.moveValid(k, i, j, board)==false)
						{
							index = 0;
						}
					}
					// this stament is evaluated to true if no pieces are can attack the kings potentiol spot
					if(index==0)
					{
						return true;
					}
				}
			}
		}
		// returns false if there is no way that the king can get out of there
		return false;
	}
}