package framework;

import resources.Config;


public class CharacterMover extends SpriteMover {
	private int fixedDrawDirection;
	private boolean stayStill = true;

	
	public CharacterMover(int x, int y, int direction, int speed)
	{
		super(x, y, direction);
		
		fixedDrawDirection = direction;
		
		this.speed = speed;
	}
	
	@Override
	public void update(long elapsedTime)
	{
		if(! stayStill)
		{
			x += (Math.cos(Math.toRadians(direction)) / 10) * speed;
			y -= (Math.sin(Math.toRadians(direction)) / 10) * speed;
			
			int minX = Config.GAME_AREA_X;
			int maxX = minX + Config.GAME_AREA_WIDTH - Config.SPRITE_NORMAL_SHIP_MAX_WIDTH;
			
			if(x < minX)
			{
				x = minX;
			}
			
			if(x > maxX)
			{
				x = maxX;
			}
		}
		
		/*
		x += (Math.cos(Math.toRadians(direction)) / elapsedTime) * speed;
		y -= Math.sin(Math.toRadians(direction)) * speed * (elapsedTime / 10);
		*/
		
		//System.out.println("New coords: x=" + x + "\ty=" + y);
	}
	
	@Override
	public int getDirection()
	{
		// Since I have to draw the character always facing the enemies, even if it's sliding left or right,
		// I save its drawing direction and return it every time someone asks for it.
		return fixedDrawDirection;
	}
	
	
	public void setStayStill(boolean stayStill)
	{
		this.stayStill = stayStill;
	}
	
}
