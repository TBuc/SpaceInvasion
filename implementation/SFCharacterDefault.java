package implementation;

import java.awt.Image;
import java.util.List;

import resources.Resources;
import framework.Animation;
import framework.ISpriteFactory;
import framework.PowerMeter;
import framework.SpriteMover;
import framework.CharacterMover;


public class SFCharacterDefault implements ISpriteFactory {
	private static long objectCounter = 0;
	private long myNumber = objectCounter++;
	
	Resources res;
	
	
	public SFCharacterDefault(Resources res)
	{
		this.res = res;
	}
	
	@Override
	public SpriteMover createMover(int x, int y, int direction)
	{
		int speed = res.CHARACTER_DEFAULT_SPEED + res.random.nextInt(10);
		
		return new CharacterMover(x, y, direction, speed);
	}
	
	@Override
	public Animation createMoveAnimation()
	{
		Animation anim = new Animation(true, false);
		
		List<Image> images = res.getCharacterDefaultMoveAnim();
		
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
		
		List<Image> images = res.getCharacterDefaultDieAnim();
		
		for(int i=0; i<images.size(); i++)
		{
			anim.addFrame(images.get(i));
		}
		
		return anim;
	}
	
	@Override
	public String getName()
	{
		return res.CHARACTER_DEFAULT_NAME + "" + myNumber;
	}
	
	@Override
	public PowerMeter createPowerMeter()
	{
		return new PowerMeter(res.CHARACTER_DEFAULT_INITIAL_POWER);
	}
	
}
