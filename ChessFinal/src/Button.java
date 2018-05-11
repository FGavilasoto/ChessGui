import javax.swing.JButton;
import javax.swing.border.*;
import java.awt.Color;
import java.awt.Font;
public class Button
{
	private JButton button;
	private int buttonRow;
	private int buttonCollom;
	private Font font;
	Button()
	{
		button = new JButton("");
		buttonRow = 0;
		buttonCollom = 0;
		font = new Font("Serif", Font.BOLD, 20);
	}
	Button(int row, int collom)
	{
		button = new JButton("");
		buttonRow = row;
		buttonCollom = collom;
		font = new Font("Serif", Font.BOLD, 30);
	}
	public void setText(String text)
	{
		button.setText(text);
		button.setFont(font);
	}
	public void setBorders(Color color, int width)
	{
		button.setBorder(new LineBorder(color, width));
	}
	public JButton getButton()
	{
		return button;
	}
	public String getText()
	{
		return button.getText();
	}
	public int getRow()
	{
		return this.buttonRow;
	}
	public int getCollom()
	{
		return this.buttonCollom;
	}
	public void setRow(int row)
	{
		this.buttonRow = row;
	}
	public void setCollom(int collom)
	{
		this.buttonCollom = collom;
	}
	public void enableButton()
	{
		button.setEnabled(true);
	}
	public void disableButton()
	{
		button.setEnabled(false);
	}
	public void setColor(Color color)
	{
		this.button.setBackground(color);
	}
	public void setColor(int r, int g, int b)
	{
		this.button.setBackground(new Color(r, g, b));
	}
}
