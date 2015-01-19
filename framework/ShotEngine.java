package framework;

import implementation.FighterSpaceShip;
import resources.Resources;


public class ShotEngine {
	protected GamePlayScreen gamePlay;
	protected Sprite parentSprite;
	protected Resources res;
	protected Resources.SHOTS shotType;
	
	protected int relativeX;
	protected int relativeY;
	
	protected int minFrequency;
	protected int minSpeed;
	
	protected int nextLaunchCountdown;
	
	protected ShotEngine(GamePlayScreen gamePlay, Sprite parentSprite, Resources res, Resources.SHOTS shotType, int relativeX, int relativeY, int minFrequency, int minSpeed)
	{
		this.gamePlay = gamePlay;
		this.parentSprite = parentSprite;
		this.relativeX = relativeX;
		this.relativeY = relativeY;
		this.minFrequency = minFrequency;
		this.minSpeed = minSpeed;
		this.shotType = shotType;
		
		this.nextLaunchCountdown = calculateNextLaunchTime();
	}
	
	private int calculateNextLaunchTime()
	{
		return minFrequency + res.random.nextInt(res.SHOT_LAUNCH_TIMER_ENTROPY);
	}
	
	public void update(long elapsedTime, int x, int y, int direction)
	{
		 nextLaunchCountdown -= elapsedTime;
		
		if(nextLaunchCountdown <= 0)
		{
			Sprite shot = null;
			
			switch(shotType)
			{
			case LASER_SHOT:
				shot = new Sprite(gamePlay, new FighterSpaceShip(res), x + relativeX, y + relativeY, direction);
				break;
			case CANNON_SHOT:
				
				break;
			case DRUNK_CANNON_SHOT:
				
						break;
			}
			
			
			try {
				shot.setParent(parentSprite);
				gamePlay.addEntity(shot);
			} catch(NullPointerException e)
			{
				// Do nothing
			}
			
			nextLaunchCountdown = calculateNextLaunchTime();
		}
	}
	
}
