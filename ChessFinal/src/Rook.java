
public class Rook extends Piece 
{
	public Rook(int x, int y, String ID) 
	{
		super(x, y, ID);
	}
	public boolean moveValid(int x, int y, Board board) 
	{
		if(board.getSize()>getOrigin()&&x>=getOrigin()&&x<board.getSize()&&y<board.getSize()&&y>=getOrigin())
		{
			if((rateOfChange(getX(), x)*rateOfChange(getY(), y))==0)
			{
				if(rateOfChange(getX(), x)==0)
				{
					if(y>getY())
					{
						do
						{
							y--;
							if(board.getSpace(x, y)!=board.getEmptyValue()&&y!=getY())
							{
								return false;
							}
						}while(y>getY());
						return true;
					}
					else if(y<getY())
					{
						do
						{
							y++;
							if(board.getSpace(x, y)!=board.getEmptyValue()&&y!=getY())
							{
								return false;
							}
						}while(y<getY());
						return true;
					}
				}
				else if(rateOfChange(getY(), y)==0)
				{
					if(x>getX())
					{
						do
						{
							x--;
							if(board.getSpace(x, y)!=board.getEmptyValue()&&x!=getX())
							{
								return false;
							}
						}while(x>getX());
						return true;
					}
					else if(x<getX())
					{
						do
						{
							x++;
							if(board.getSpace(x, y)!=board.getEmptyValue()&&x!=getX())
							{
								return false;
							}
						}while(x<getX());
						return true;
					}
				}
			}
		}
		return false;
	}
}
