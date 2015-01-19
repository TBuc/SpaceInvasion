package framework;


public abstract class SpriteMover {
	protected double x;
	protected double y;
	protected int direction;
	protected double speed;
	
	
	public SpriteMover()
	{
		this(0, 0);
	}
	
	public SpriteMover(int x, int y)
	{
		this(x, y, 0);
	}
	
	public SpriteMover(int x, int y, int direction)
	{
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	
	abstract public void update(long elapsedTime);
	
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public void setDirection(int direction)
	{
		this.direction = direction;
	}
	
	
	public final int getX()
	{
		return (int)Math.floor(x);
	}
	
	public final int getY()
	{
		return (int)Math.floor(y);
	}
	
	// Overridable for character's behavior (sliding) 
	public int getDirection()
	{
		return direction;
	}
}
