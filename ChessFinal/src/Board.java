// this is the superclass for the ChessBoard class
import java.awt.Color;

import javax.swing.JButton;
public class Board 
{
	private int size;
	private String emptyValue;
	private String[][] spaces;
	Board()
	{
		this.size = 8;
		this.emptyValue = "----";
		this.spaces = new String[this.size][this.size];
		for(int i = 0; i<this.size; i++)
		{
			for(int j = 0; j<this.size; j++)
			{
				this.spaces[i][j] = this.emptyValue;
			}
		}
	}
	Board(int size, String emptyValue)
	{
		if(size>1)
		{
			this.size = size;
		}
		else
		{
			this.size = 8;
		}
		this.emptyValue = emptyValue;
		this.spaces = new String[this.size][this.size];
		for(int i = 0; i<this.size; i++)
		{
			for(int j = 0; j<this.size; j++)
			{
				this.spaces[i][j] = this.emptyValue;
			}
		}
	}
	public void printBoard()
	{
		for(int i = 0; i<this.size; i++)
		{
			System.out.print(i+"  ");
			for(int j = 0; j<this.size; j++)
			{
				System.out.print(spaces[i][j]+" ");
				if(j==this.size-1)
				{
					System.out.println();
				}
			}
		}
		for(int i = 0; i<this.size; i++)
		{
			System.out.print("    "+i);
		}
	}
	public int getSize() 
	{
		return size;
	}
	public String getSpace(int i, int j) 
	{
		return spaces[i][j];
	}
	public void setSpace(String space, int i, int j) 
	{
		this.spaces[i][j] = space;
	}
	public String getEmptyValue()
	{
		return this.emptyValue;
	}
	public void setEmptyValue(String emptyValue)
	{
		for(int i = 0; i<this.size; i++)
		{
			for(int j = 0; j<this.size; j++)
			{
				if(spaces[i][j]==this.emptyValue)
				{
					this.spaces[i][j] = emptyValue;
				}
			}
		}
		this.emptyValue = emptyValue;
	}
	public void ResetSpace(int i, int j)
	{
		this.spaces[i][j] = this.emptyValue;
	}
	public void placeArrayOfPiecesOnBoard(Object player)
	{
		if(player instanceof Player )
		{
			for(int i = 0; i<((Player)player).sizeOfList(); i++)
			{
				setSpace(((Player)player).getID(i),((Player)player).getX(i),((Player)player).getY(i));
			}
		}
	}
}
