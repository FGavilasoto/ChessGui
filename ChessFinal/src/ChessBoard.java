import java.awt.Color;


public class ChessBoard extends Board {

	public ChessBoard() 
	{
		
	}
	public ChessBoard(int size, String emptyValue) 
	{
		super(size, emptyValue);
	}
	public void setSpace(String space, int i, int j)
	{
		if(i>=0&&i<getSize()&&j>=0&&j<getSize())
		{
			if(getSpace(i, j)==getEmptyValue())
			{
				super.setSpace(space, i, j);
			}
		}
	}
}
