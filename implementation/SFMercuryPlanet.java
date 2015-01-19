package implementation;

import java.awt.Image;
import java.util.List;

import resources.Resources;
import framework.Animation;
import framework.ISpriteFactory;
import framework.LinearSpriteMover;
import framework.PowerMeter;
import framework.SpriteMover;


public class SFMercuryPlanet implements ISpriteFactory {
	
	private static long objectCounter = 0;
	private long myNumber = objectCounter++;
	
	Resources res;
	int gameLevel;
	
	
	public SFMercuryPlanet(Resources res, int level)
	{
		this.res = res;
		this.gameLevel = level;
	}
	
	@Override
	public SpriteMover createMover(int x, int y, int direction)
	{
		int speed = res.MERCURY_PLANET_SPEED + (int)Math.floor(gameLevel / 10);
		
		return new LinearSpriteMover(x, y, direction, speed);
	}
	
	@Override
	public Animation createMoveAnimation()
	{
		Animation anim = new Animation(true, false);
		
		List<Image> images = res.getPlanetMercury_moveAnim();
		
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
		anim.setFrameDuration(400);
		anim.setFadingDuration(200);
		
		List<Image> images = res.getPlanetMercury_dieAnim();
		
		for(int i=0; i<images.size(); i++)
		{
			anim.addFrame(images.get(i));
		}
		
		return anim;
	}
	
	@Override
	public String getName()
	{
		return res.MERCURY_PLANET_NAME + "" + myNumber;
	}
	
	@Override
	public PowerMeter createPowerMeter()
	{
		return new PowerMeter(res.MERCURY_PLANET_INITIAL_POWER + gameLevel);
	}
	
}
