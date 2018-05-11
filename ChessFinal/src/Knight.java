// class for the Knight piece
public class Knight extends Piece 
{
	public Knight(int x, int y, String ID) 
	{
		super(x, y, ID);
	}
	/*
	 * 
	 * 
	 */
	public boolean moveValid(int x, int y, Board board) 
	{
		if(board.getSize()>getOrigin()&&x>=getOrigin()&&x<board.getSize()&&y<board.getSize()&&y>=getOrigin())
		{
			if((rateOfChange(getX(), x)==1&&rateOfChange(getY(), y)==2)||(rateOfChange(getX(), x)==2&&rateOfChange(getY(), y)==1))
			{
				return true;
			}
		}
		return false;
	}
}
