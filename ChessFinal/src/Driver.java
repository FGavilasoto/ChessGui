// the UI for the program
//Ferdinand Avila-Soto, Nicholas Bulinski
import java.util.Scanner;
public class Driver
{
	public static void main(String[] args)
	{
		new Driver().UI();
	}
	public void UI()
	{
		Scanner input = new Scanner(System.in);
		ChessBoard board = new ChessBoard();
		HumanPlayer player1 = new HumanPlayer(board.getSize(), 1);
		HumanPlayer player2 = new HumanPlayer(board.getSize(), 2);
		System.out.print("do you want to see the rules befor you play(y/n)? ");
		char choice = input.nextLine().charAt(0);
		if(choice=='y'||choice=='Y')
		{
			printInstructions();
			System.out.println();
		}
		board.placeArrayOfPiecesOnBoard(player1);
		board.placeArrayOfPiecesOnBoard(player2);
		while(true)
		{
			System.out.print("do you wish to exit the game(y/n)?");
			char select = input.nextLine().charAt(0);
			if(select=='y'||select=='Y')
			{
				break;
			}
			playerTurn(player1, player2, board);
			if(checkmate(player1, player2, player2.getX("_K2_"), player2.getY("_K2_"), board)==true)
			{
				System.out.println("checkmate player one wins");
				break;
			}
			playerTurn(player2, player1, board);
			if(checkmate(player2, player1, player1.getX("_K1_"), player1.getY("_K1_"), board)==true)
			{
				System.out.println("checkmate player two wins");
				break;
			}
		}
		System.out.println();
		System.out.print("do you want to play again(y/n)? ");
		choice = input.nextLine().charAt(0);
		if(choice=='y'||choice=='Y')
		{
			UI();
		}
		else
		{
			System.out.println("good bye");
		} 
	}
	public void playerTurn(Player playersTurn, Player otherPlayer, Board board)
	{
		Scanner input = new Scanner(System.in);
		int row;
		int collom;
		String ID;
		board.printBoard();
		System.out.println();
		System.out.println();
		System.out.println("player"+playersTurn.getOrder());
		while(true)
		{
			System.out.println();
			System.out.print("enter the ID tag of the piece that you wish to move(must be 4 chars long): ");
			ID = input.nextLine();
			System.out.println();
			if(playersTurn.validID(ID)==false)
			{
				System.out.println("that is an invalid input");
				continue;
			}
			System.out.print("enter the row where you want to move that piece: ");
			row = input.nextInt();
			input.nextLine();
			if(row<0||row>board.getSize())
			{
				System.out.println();
				System.out.println("that is an invalid input");
				continue;
			}
			System.out.print("enter the collom where you want to move that piece: ");
			collom = input.nextInt();
			input.nextLine();
			if(collom<0||collom>board.getSize())
			{
				System.out.println();
				System.out.println("that is an invalid input");
				continue;
			}
			if(playersTurn.moveValid(ID, row, collom, board)==false||playersTurn.validID(board.getSpace(row, collom))==true)
			{
				System.out.println();
				System.out.println("that is an invalid move for that piece");
				continue;
			}
			else
			{
				break;
			}
		}
		if(isAttack(row, collom, otherPlayer, board)==true)
		{
			takePiece(playersTurn, otherPlayer, board, row, collom, ID);
		}
		else
		{
			movePiece(playersTurn, board, row, collom, ID);
		}
	}
	public boolean isAttack(int newRow, int newCollom, Player defendingPlayer, Board board)
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
	public void movePiece(Player player, Board board, int newRow, int newCollom, String ID)
	{
		board.ResetSpace(player.getX(ID), player.getY(ID));
		player.setX(ID, newRow);
		player.setY(ID, newCollom);
		board.setSpace(ID, newRow, newCollom);
	}
	public void takePiece(Player attakingPlayer, Player defendingPlayer, Board board, int newRow, int newCollom, String ID)
	{
		defendingPlayer.remove(board.getSpace(newRow, newCollom));
		board.ResetSpace(newRow, newCollom);
		board.ResetSpace(attakingPlayer.getX(ID), attakingPlayer.getY(ID));
		attakingPlayer.setX(ID, newRow);
		attakingPlayer.setY(ID, newCollom);
		board.setSpace(ID, newRow, newCollom);
	}
	public boolean checkmate(Player winingPlayer, Player loseingPlayer, int kingsRow, int kingsCollom, Board board)
	{
		if(isInCheck(winingPlayer, kingsRow, kingsCollom, board)==true)
		{
			if(canMoveOutOfCheck(winingPlayer, loseingPlayer, kingsRow, kingsCollom, board)==false)
			{
				return true;
			}
		}
		return false;
	}
	public boolean isInCheck(Player player, int kingsRow, int kingsCollom, Board board)
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
	public boolean canMoveOutOfCheck(Player winingPlayer, Player loseingPlayer,int kingsRow, int kingsCollom, Board board)
	{
		String ID = loseingPlayer.getID(kingsRow, kingsCollom);
		for(int i = 0; i<board.getSize(); i++)
		{
			for(int j = 0; j<board.getSize(); j++)
			{
				if(loseingPlayer.moveValid(ID, i, j, board)==true)
				{
					for(int k = 0; k<winingPlayer.sizeOfList(); k++)
					{
						if(winingPlayer.moveValid(k, i, i, board)==false)
						{
							return true;
						}
					}
				}
			}
			return false;
		}
		return true;
	}
	public boolean canAttack(Piece piece, int newRow, int newCollom, Board board, Player defendingPlayer)
	{
		if(piece.moveValid(newRow, newCollom, board)==true&&defendingPlayer.validID(board.getSpace(newRow, newCollom))==true)
		{
			return true;
		}
		return false;
	}
	public void printInstructions()
	{
		System.out.println("this is a game of chess and the standerd rules apply");
		System.out.println();
		System.out.println("1 how to move:");
		System.out.println("you will enter the ID tag of the piece that you want to move (ex: knight = N2-2");
		System.out.println("there must be no pieces blocking the path of you selection or the move will be invalid");
		System.out.println("the only exseption is that of the kinght piece which can jump over any piece");
		System.out.println("the first char N is the type of piece, the second is the player number");
		System.out.println("the third is the number that is present if there is mutipuls of the same type seprated by a -");
		System.out.println("you will then enter the row(0-7) and collom(0-1) that you want to move the piece to");
		System.out.println("the rules for moving the pieces are as follows");
		System.out.println("king will move in a squere with the ID of Kp (p is for player number ex K1, K2)");
		System.out.println("knights will move in a two up one over or one up two over format; there ID is Np-# (# is for number of the piece)");
		System.out.println("pawns can move fowerd only one at a time exsept on its first turn when it can move two");
		System.out.println("also pawns move in only one directon and attack diagonaly(we will get to that later); there ID is Pp-#");
		System.out.println("bishops move only diagonaly with a slop of one; there ID is Bp-#");
		System.out.println("rooks can only move in a state line (up, down, left, and right); they have an ID of Rp-#");
		System.out.println("finaly the queen can move like a rook(strate line) or a bishop(diangonaly); she has an ID of Qp");
		System.out.println();
		System.out.println("2 how to attack:");
		System.out.println("you attack the same way you move");
		System.out.println("all you do is simply select a space with an opposing piece on it");
		System.out.println("same rules as with moveing only that pawns can only attack diagonaly");
		System.out.println();
		System.out.println("3 how to win:");
		System.out.println("to win the game you must checkmate the opposing players king");
		System.out.println("to do this you must first put the king in check by puting you piece in a position to attack the king");
		System.out.println("the opposing player must respond to this by either killing you piece,");
		System.out.println("moving a piece inbetween the king and your piece, or moving the king to a spot not in check");
		System.out.println("if none of these are possible it is a checkmate and you win the game");
		System.out.println("however the same rules apley to you as well and the opposing player will try to checkmate you as well");
	}
}