package implementation;

import java.awt.Image;
import java.util.List;

import resources.Resources;
import framework.Animation;
import framework.LinearSpriteMover;
import framework.PowerMeter;
import framework.ISpriteFactory;
import framework.SpriteMover;


public class SFFighterSpaceShip implements ISpriteFactory {
	
	private static long objectCounter = 0;
	private long myNumber = objectCounter++;
	
	Resources res;
	int gameLevel;
	double levelMultiplyFactor = 1;
	
	
	public SFFighterSpaceShip(Resources res, int level)
	{
		this.res = res;
		this.gameLevel = level;
		
		levelMultiplyFactor = Math.log10(gameLevel) + 1;
	}
	
	@Override
	public SpriteMover createMover(int x, int y, int direction)
	{
		int speed = (int)Math.floor(res.FIGHTER_SPACESHIP_SPEED * levelMultiplyFactor);
		
		return new LinearSpriteMover(x, y, direction, speed);
	}
	
	@Override
	public Animation createMoveAnimation()
	{
		Animation anim = new Animation(true, false);
		
		List<Image> images = res.getFighterSpaceShipMoveAnim();
		
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
		
		List<Image> images = res.getFighterSpaceShipDieAnim();
		
		for(int i=0; i<images.size(); i++)
		{
			anim.addFrame(images.get(i));
		}
		
		return anim;
	}
	
	@Override
	public String getName()
	{
		return res.FIGHTER_SPACESHIP_NAME + "" + myNumber;
	}

	@Override
	public PowerMeter createPowerMeter()
	{
		int power = (int)Math.floor(res.FIGHTER_SPACESHIP_INITIAL_POWER * levelMultiplyFactor);
		
		return new PowerMeter(power);
	}
	
}
