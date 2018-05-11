
public class Pawn extends Piece
{
	private int direction;
	private final int  UP;
	private final int DOWN;
	private boolean firstTurn;
	private boolean directionSet;
	public Pawn(int x, int y, String ID) 
	{
		super(x, y, ID);
		this.directionSet = false;
		this.DOWN = 1;
		this.UP = -1;
		this.firstTurn = true;
	}
	private void setDirection(int oldX, int boardSize)
	{
		if(getX()>boardSize/2)
		{
			this.direction = this.UP;
		}
		else
		{
			this.direction = this.DOWN;
		}
	}
	public boolean moveValid(int x, int y, Board board) 
	{
		if(this.directionSet==false)
		{
			setDirection(getX(), board.getSize());
			this.directionSet = true;
		}
		if(board.getSize()>getOrigin()&&x>=getOrigin()&&x<board.getSize()&&y<board.getSize()&&y>=getOrigin())
		{
			if(this.direction==this.UP)
			{
				if(this.firstTurn&&(getX()-x)>0&&rateOfChange(getY(), y)==0&&rateOfChange(getX(), x)==2&&board.getSpace(getX()-1, y).compareTo(board.getEmptyValue())==0)
				{
					this.firstTurn = false;
					return true;
				}
				else if(getX()==6&&(getX()-x)>0&&rateOfChange(getY(), y)==0&&rateOfChange(getX(), x)==2)
				{
					return true;
				}
				else if((getX()-x)>0&&rateOfChange(getY(), y)==0&&rateOfChange(getX(), x)==1&&board.getSpace(getX()-1, y).compareTo(board.getEmptyValue())==0)
				{
					return true;
				}
				else if((getX()-x)>0&&rateOfChange(getY(), y)==1&&rateOfChange(getX(), x)==1&&board.getSpace(x,y)!=board.getEmptyValue())
				{
					return true;
				}
			}
			else if(this.direction==this.DOWN)
			{
				if(this.firstTurn&&(getX()-x)<0&&rateOfChange(getY(), y)==0&&rateOfChange(getX(), x)==2&&board.getSpace(getX()+1, y).compareTo(board.getEmptyValue())==0)
				{
					this.firstTurn = false;
					return true;
				}
				else if((getX()-x)<0&&rateOfChange(getY(), y)==0&&rateOfChange(getX(), x)<=1&&board.getSpace(getX()+1, y).compareTo(board.getEmptyValue())==0)
				{
					return true;
				}
				else if((getX()-x)<0&&rateOfChange(getY(), y)==1&&rateOfChange(getX(), x)==1&&board.getSpace(x,y)!=board.getEmptyValue())
				{
					return true;
				}
			}
		}
		return false;
	}
}
