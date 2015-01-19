package framework;



public class NullSpriteMover extends SpriteMover {

	public NullSpriteMover(int x, int y, int direction)
	{
		super(x, y, direction);
		
		speed = 0;
	}
	
	@Override
	public void update(long elapsedTime)
	{
		
	}
}