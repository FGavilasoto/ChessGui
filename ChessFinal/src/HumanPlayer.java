
public class HumanPlayer extends Player 
{
	HumanPlayer()
	{
		
	}
	HumanPlayer(int order) 
	{
		super(order);
	}
	HumanPlayer(int boardSize, int order)
	{
		super(order);
		if(order>=1&&order<=2)
		{
			if(order==2)
			{
				addRowOfRoyalPieces(0, boardSize, order);
				addRowOfPawns(1, boardSize, order);
			}
			else if(order==1)
			{
				addRowOfRoyalPieces(boardSize-1, boardSize, order);
				addRowOfPawns(boardSize-2, boardSize, order);
			}
		}
	}
	private void addRowOfPawns(int i, int boardSize, int order)
	{
		for(int j = 0; j<boardSize; j++)
		{
			if(j>=boardSize)
			{
				break;
			}
			add(new Pawn(i,j,"P"+order+"-"+(j+1)));
		}
	}
	private void addRowOfRoyalPieces(int i, int boardSize, int order)
	{
		add(new Rook(i,0,("R"+order+"-"+(1))));
		add(new Rook(i,boardSize-1,"R"+order+"-"+(2)));
		add(new Knight(i,1,"N"+order+"-"+(1)));
		add(new Knight(i,boardSize-2,"N"+order+"-"+(2)));
		add(new Bishop(i,2,"B"+order+"-"+(1)));
		add(new Bishop(i,boardSize-3,"B"+order+"-"+(2)));
		if(order==1)
		{
			add(new Queen(i,3,"_Q"+order+"_"));
			add(new King(i,4,"_K"+order+"_"));
		}
		else
		{
			add(new Queen(i,4,"_Q"+order+"_"));
			add(new King(i,3,"_K"+order+"_"));
		}
	}
}