package implementation;

import java.awt.Image;
import java.util.List;

import resources.Resources;
import framework.Animation;
import framework.ISpriteFactory;
import framework.LinearSpriteMover;
import framework.PowerMeter;
import framework.SpriteMover;


public class SFLaserShot implements ISpriteFactory {
	private static long objectCounter = 0;
	private long myNumber = objectCounter++;
	
	Resources res;
	
	
	public SFLaserShot(Resources res)
	{
		this.res = res;
	}
	
	@Override
	public SpriteMover createMover(int x, int y, int direction)
	{
		int speed = res.LASER_SHOT_SPEED;
		
		return new LinearSpriteMover(x, y, direction, speed);
	}
	
	@Override
	public Animation createMoveAnimation()
	{
		Animation anim = new Animation(true, false);

		List<Image> images = res.getShotLaserMoveAnim();
		
		for(int i=0; i<images.size(); i++)
		{
			anim.addFrame(images.get(i));
		}
		
		return anim;
	}
	
	@Override
	public Animation createDieAnimation()
	{
		Animation anim = new Animation(false, false);
		
		List<Image> images = res.getShotLaserDieAnim();
		
		for(int i=0; i<images.size(); i++)
		{
			anim.addFrame(images.get(i));
		}
		
		return anim;
	}
	
	@Override
	public String getName()
	{
		return res.LASER_SHOT_NAME + "" + myNumber;
	}
	
	@Override
	public PowerMeter createPowerMeter()
	{
		return new PowerMeter(res.LASER_SHOT_INITIAL_POWER);
	}
	
}
