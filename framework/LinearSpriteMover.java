package framework;


public class LinearSpriteMover extends SpriteMover {
	
	public LinearSpriteMover(int x, int y, int direction, int speed)
	{
		super(x, y, direction);
		
		this.speed = speed;
	}
	
	@Override
	public void update(long elapsedTime)
	{
		x += Math.cos(Math.toRadians(direction)) * (speed / 10);
		y -= Math.sin(Math.toRadians(direction)) * (speed / 10);
		
		
		/*
		x += (Math.cos(Math.toRadians(direction)) / elapsedTime) * speed;
		y -= Math.sin(Math.toRadians(direction)) * speed * (elapsedTime / 10);
		*/
		
		//System.out.println("New coords: x=" + x + "\ty=" + y);
	}
}
