
public class Bishop extends Piece 
{
	public Bishop(int x, int y, String ID) 
	{
		super(x, y, ID);
	}
	public boolean moveValid(int x, int y, Board board) 
	{
		if(board.getSize()>getOrigin()&&x>=getOrigin()&&x<board.getSize()&&y<board.getSize()&&y>=getOrigin())
		{
			if((rateOfChange(getX(), x)==rateOfChange(getY(), y)))
			{
				if(x>getX())
				{
					if(y>getY())
					{
						do
						{
							x--;
							y--;
							if(board.getSpace(x, y)!=board.getEmptyValue()&&y!=getY())
							{
								return false;
							}
						}while(y>getY());
						return true;
					}
					else
					{
						do
						{
							x--;
							y++;
							if(board.getSpace(x, y)!=board.getEmptyValue()&&y!=getY())
							{
								return false;
							}
						}while(y<getY());
						return true;
					}
				}
				else
				{
					if(y>getY())
					{
						do
						{
							x++;
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
							x++;
							y++;
							if(board.getSpace(x, y)!=board.getEmptyValue()&&y!=getY())
							{
								return false;
							}
						}while(y<getY());
						return true;
					}
				}
			}
		}
		return false;
	}
}
