package implementation;

import java.awt.Image;
import java.util.List;

import resources.Resources;
import framework.Animation;
import framework.LinearSpriteMover;
import framework.PowerMeter;
import framework.ISpriteFactory;
import framework.SpriteMover;


public class SFInterceptorSpaceShip implements ISpriteFactory {
	
	private static long objectCounter = 0;
	private long myNumber = objectCounter++;
	
	Resources res;
	int gameLevel;
	
	
	public SFInterceptorSpaceShip(Resources res, int level)
	{
		this.res = res;
		this.gameLevel = level;
	}
	
	@Override
	public SpriteMover createMover(int x, int y, int direction)
	{
		int speed = res.INTERCEPTOR_SPACESHIP_SPEED + gameLevel;
		
		return new LinearSpriteMover(x, y, direction, speed);
	}
	
	@Override
	public Animation createMoveAnimation()
	{
		Animation anim = new Animation(true, false);
		
		List<Image> images = res.getInterceptorSpaceShipMoveAnim();
		
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
		
		List<Image> images = res.getInterceptorSpaceShipDieAnim();
		
		for(int i=0; i<images.size(); i++)
		{
			anim.addFrame(images.get(i));
		}
		
		return anim;
	}
	
	@Override
	public String getName()
	{
		return res.INTERCEPTOR_SPACESHIP_NAME + "" + myNumber;
	}

	@Override
	public PowerMeter createPowerMeter()
	{
		return new PowerMeter(res.INTERCEPTOR_SPACESHIP_INITIAL_POWER + gameLevel);
	}
	
}
