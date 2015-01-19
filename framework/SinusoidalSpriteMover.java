package framework;

import resources.Resources;


public class SinusoidalSpriteMover extends SpriteMover {
	private int linearCyclesLeft = 30;
	
	private int curDirection;
	
	RangeNavigator rangeNavi;
	
	
	public SinusoidalSpriteMover(int x, int y, int direction, int speed)
	{
		super(x, y, direction);
		
		this.speed = speed;
		
		this.curDirection = direction;
		
		rangeNavi = new RangeNavigator(-40, 40, 5, 0);
		
		rangeNavi.setReverseNavigation(Resources.getInstance().random.nextBoolean());
	}
	
	@Override
	public void update(long elapsedTime)
	{
		if(linearCyclesLeft > 0)
		{
			linearCyclesLeft--;
		}
		else
		{
			curDirection = direction + rangeNavi.getNext();
		}
		
		x += calculateSpeedX(curDirection);
		y -= calculateSpeedY(curDirection);
		
		
		/*
		x += (Math.cos(Math.toRadians(direction)) / elapsedTime) * speed;
		y -= Math.sin(Math.toRadians(direction)) * speed * (elapsedTime / 10);
		*/
		
		//System.out.println("New coords: direction=" + curDirection + "\tx=" + x + "\ty=" + y);
	}

	private double calculateSpeedX(int directionDegrees)
	{
		return Math.cos(Math.toRadians(directionDegrees)) * (speed / 10);
	}
	
	private double calculateSpeedY(int directionDegrees)
	{
		//return Math.sin(Math.toRadians(directionDegrees)) * (speed / 10);
		return (speed / 10);
	}
}
