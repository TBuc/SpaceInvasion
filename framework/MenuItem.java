package framework;

public class MenuItem {
	private String text;
	private int screenID;
	
	public MenuItem(String text, int screenID)
	{
		this.text = text;
		this.screenID = screenID;
	}
	
	public String getText()
	{
		return text;
	}
	
	public int getScreenID()
	{
		return screenID;
	}
}
