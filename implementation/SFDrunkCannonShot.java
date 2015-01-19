package implementation;

import resources.Resources;
import framework.Animation;
import framework.ISpriteFactory;
import framework.PowerMeter;
import framework.SinusoidalSpriteMover;
import framework.SpriteMover;


public class SFDrunkCannonShot implements ISpriteFactory {
	private static long objectCounter = 0;
	private long myNumber = objectCounter++;
	
	Resources res;
	
	
	public SFDrunkCannonShot(Resources res)
	{
		this.res = res;
	}
	
	@Override
	public SpriteMover createMover(int x, int y, int direction)
	{
		int speed = res.DRUNK_CANNON_SHOT_SPEED;
		
		return new SinusoidalSpriteMover(x, y, direction, speed);
	}
	
	@Override
	public Animation createMoveAnimation()
	{
		Animation anim = new Animation(true, false);
		
		anim.addFrame(res.getSmallCannonShot());
		
		return anim;
	}

	@Override
	public Animation createDieAnimation()
	{
		Animation anim = new Animation(false, false);
		
		anim.addFrame(res.getSmallCannonShotDies());
		
		return anim;
	}
	
	@Override
	public String getName()
	{
		return res.SMALL_CANNON_SHOT_NAME + "" + myNumber;
	}
	
	@Override
	public PowerMeter createPowerMeter()
	{
		return new PowerMeter(res.DRUNK_CANNON_SHOT_INITIAL_POWER);
	}
	
}
