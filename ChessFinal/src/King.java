
public class King extends Piece 
{
	public King(int x, int y, String ID) 
	{
		super(x, y, ID);
	}
	public boolean moveValid(int x, int y, Board board) 
	{
		if(board.getSize()>getOrigin()&&x>=getOrigin()&&x<board.getSize()&&y<board.getSize()&&y>=getOrigin())
		{
			if((rateOfChange(getX(), x)*rateOfChange(getY(), y)==0)&&(rateOfChange(getX(), x)==1||rateOfChange(getY(), y)==1)
					||(rateOfChange(getX(), x)*rateOfChange(getY(), y))==1)
			{
				return true;
			}
		}
		return false;
	}
}
