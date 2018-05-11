// super class for all of the pieces
public abstract class Piece 
{
	private int x;
	private int y;
	private String ID;
	private final int ORIGIN;
	Piece(int x, int y, String ID)
	{
		this.ORIGIN = 0;
		if(x>=this.ORIGIN)
		{
			this.x = x;
		}
		if(y>=this.ORIGIN)
		{
			this.y = y;
		}
		this.ID = ID;
	}
	public int getOrigin()
	{
		return this.ORIGIN;
	}
	public int getX()
	{
		return this.x;
	}
	public int getY()
	{
		return this.y;
	}
	public String getID()
	{
		return ID;
	}
	public abstract boolean moveValid(int x, int y, Board board);
	public int rateOfChange(int oldPosition, int newPosition)
	{
		return Math.abs((oldPosition-newPosition));
	}
	public void setX(int x)
	{
		if(x>=this.ORIGIN)
		{
			this.x = x;
		}
	}
	public void setY(int y)
	{
		if(y>=this.ORIGIN)
		{
			this.y = y;
		}
	}
	protected void setID(String ID)
	{
		this.ID = ID;
	}
	public String toString()
	{
		return ("x: "+this.x+" y: "+this.y+" ID: "+this.ID);
	}
	public boolean equals(Object otherPiece)
	{
		if(otherPiece instanceof Piece)
		{
			if(this.x==((Piece) otherPiece).getX()&&this.y==((Piece) otherPiece).getY())
			{
				return true;
			}
		}
		return false;
	}
}