// holds all of the piece objects so all we have to do is call the player class to move a piece
// this is the superclass of the HumanPlayer Class
import java.util.ArrayList;
public abstract class Player 
{
	private ArrayList<Piece> pieces;
	private int order;
	Player()
	{
		this.order = 1;
		this.pieces = new ArrayList<Piece>();
	}
	Player(int order)
	{
		if(order>0)
		{
			this.order = order;
		}
		else
		{
			this.order = 1;
		}
		this.pieces = new ArrayList<Piece>();
	}
	public int getOrder()
	{
		return this.order;
	}
	public void setOrder(int order)
	{
		this.order = order;
	}
	public int sizeOfList()
	{
		return this.pieces.size();
	}
	public void add(Object pieceObject)
	{
		if(pieceObject instanceof Piece)
		{
			this.pieces.add((Piece)pieceObject);
		}
	}
	public void add(String ID, Object pieceObject)
	{
		if(pieceObject instanceof Piece&&validID(ID)==true)
		{
			this.pieces.add(getIndex(ID), (Piece)pieceObject);
		}
	}
	public void remove(String ID)
	{
		if(validID(ID)==true)
		{
			this.pieces.remove(getIndex(ID));
		}
	}
	public void remove(Object pieceObject)
	{
		if(pieceObject instanceof Piece)
		{
			this.pieces.remove((Piece)pieceObject);
		}
	}
	public Piece getPiece(String ID)
	{
		return this.pieces.get(getIndex(ID));	
	}
	public int getOrigin(String ID)
	{
		return this.pieces.get(getIndex(ID)).getOrigin();
	}
	public int getX(String ID)
	{
		return this.pieces.get(getIndex(ID)).getX();
	}
	public int getY(String ID)
	{
		return this.pieces.get(getIndex(ID)).getY();
	}
	public int getX(int index)
	{
		return this.pieces.get(index).getX();
	}
	public int getY(int index)
	{
		return this.pieces.get(index).getY();
	}
	public String getID(String ID)
	{
		return this.pieces.get(getIndex(ID)).getID();
	}
	public String getID(int index)
	{
		return this.pieces.get(index).getID();
	}
	public String getID(int collem, int row)
	{
		for(int i = 0; i<this.pieces.size(); i++)
		{
			if(this.pieces.get(i).getX()==row&&this.pieces.get(i).getY()==collem)
			{
				return this.pieces.get(i).getID();
			}
		}
		return "no good";
	}
	public boolean moveValid(String ID, int x, int y, Board board)
	{
		int index = getIndex(ID);
		return this.pieces.get(index).moveValid(x, y, board);
	}
	public boolean moveValid(int index, int x, int y, Board board)
	{
		return this.pieces.get(index).moveValid(x, y, board);
	}
	public void setX(String ID, int x)
	{
		if(validID(ID))
		{
			this.pieces.get(getIndex(ID)).setX(x);
		}
	}
	public void setY(String ID, int y)
	{
		if(validID(ID))
		{
			this.pieces.get(getIndex(ID)).setY(y);
		}
	}
	public String toString()
	{
		return ("order: "+this.order+" numOfPieces: "+this.pieces.size());
	}
	public String toString(String ID)
	{
		return this.pieces.get(getIndex(ID)).toString();
	}
	public boolean equals(Object otherPlayer)
	{
		if(otherPlayer instanceof Player)
		{
			if(this.order==((Player)otherPlayer).getOrder())
			{
				return true;
			}
		}
		return false;
	}
	public int getIndex(String ID)
	{
		for(int i = 0; i<this.pieces.size(); i++)
		{
			if(this.pieces.get(i).getID().equalsIgnoreCase(ID))
			{
				return i;
			}
		}
		return -1;
	}
	public boolean validID(String ID)
	{
		boolean result = false;
		for(int i = 0; i<this.pieces.size(); i++)
		{
			if(this.pieces.get(i).getID().equalsIgnoreCase(ID))
			{
				result = true;
			}
		}
		return result;
	}
	public int rateOfChange(int oldPosition, int newPosition, String ID)
	{
		return this.pieces.get(getIndex(ID)).rateOfChange(oldPosition, newPosition);
	}
}