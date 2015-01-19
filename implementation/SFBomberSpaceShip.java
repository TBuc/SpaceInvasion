package implementation;

import java.awt.Image;
import java.util.List;

import resources.Resources;
import framework.Animation;
import framework.ISpriteFactory;
import framework.LinearSpriteMover;
import framework.PowerMeter;
import framework.SpriteMover;


public class SFBomberSpaceShip implements ISpriteFactory {
	private static long objectCounter = 0;
	private long myNumber = objectCounter++;
	
	Resources res;
	int gameLevel;
	
	
	public SFBomberSpaceShip(Resources res, int level)
	{
		this.res = res;
		this.gameLevel = level;
	}
	
	@Override
	public SpriteMover createMover(int x, int y, int direction)
	{
		int speed = res.BOMBER_SPACESHIP_SPEED + gameLevel;
		
		return new LinearSpriteMover(x, y, direction, speed);
	}
	
	@Override
	public Animation createMoveAnimation()
	{
		Animation anim = new Animation(true, false);
		
		List<Image> images = res.getBomberSpaceShipMoveAnim();
		
		for(int i=0; i<images.size(); i++)
		{
			anim.addFrame(images.get(i));
		}
		
		return anim;
	}
	
	@Override
	public Animation createDieAnimation()
	{
		Animation anim = new Animation(false, true);
		anim.setFrameDuration(100);
		anim.setFadingDuration(40);
		
		List<Image> images = res.getBomberSpaceShipDieAnim();
		
		for(int i=0; i<images.size(); i++)
		{
			anim.addFrame(images.get(i));
		}
		
		return anim;
	}
	
	@Override
	public String getName()
	{
		return res.BOMBER_SPACESHIP_NAME + "" + myNumber + "-Lev" + gameLevel;
	}
	
	@Override
	public PowerMeter createPowerMeter()
	{
		return new PowerMeter(res.BOMBER_SPACESHIP_INITIAL_POWER + gameLevel);
	}
	
}
