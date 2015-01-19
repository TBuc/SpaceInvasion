package framework;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import resources.Config;


public class Sprite {
	protected static long objCounter = 0;
	
	protected IGamePlayScreen gamePlay;
	
	protected String name = "Unknown" + objCounter++;
	protected Sprite parent = null;
	
	protected SpriteMover spriteMover;
	protected Animation curAnimation;
	
	protected Animation moveAnimation;
	protected Animation dieAnimation;
	
	protected PowerMeter powerMeter;
	protected int killBonus;
	
	
	public Sprite(IGamePlayScreen gamePlay, ISpriteFactory factory, int x, int y, int direction)
	{
		this.gamePlay = gamePlay;
		
		// Mover
		spriteMover = factory.createMover(x, y, direction);
		
		// Animation
		moveAnimation = factory.createMoveAnimation();
		dieAnimation = factory.createDieAnimation();
		curAnimation = moveAnimation;
		
		// Power
		powerMeter = factory.createPowerMeter();
		
		name = factory.getName();
	}
	
	public void update(long elapsedTime)
	{	
		if(isAlive() || !curAnimation.isEnded())
		{
			curAnimation.update(elapsedTime);
			spriteMover.update(elapsedTime);
		}
	}
	
	public void draw(Graphics2D g)
	{
		Rectangle r = this.getBoundaries();
		
		// Object area
		/*
		g.setColor(Color.CYAN);
		g.fillRect(r.x, r.y, r.width, r.height);
		*/
		
		// Image
		curAnimation.draw(g, r.x, r.y, spriteMover.getDirection());
		
		// Power bar
		if(! gamePlay.isThisSpriteTheCharacter(this.getParent()))
		{
			g.setColor(Color.GREEN);
			int powerBarX = (int) (r.x + Math.floor((double)r.width / 5 * 4));
			int powerBarHeight = (int) Math.floor((double)r.height / 2);
			int powerBarLowerY = r.y + r.height;
			int curPowerHeight = (int) Math.floor(powerBarHeight * powerMeter.getCurPowerRelative());
			
			g.drawRect(powerBarX, powerBarLowerY-powerBarHeight, Config.POWER_BAR_WIDTH, powerBarHeight);
			
			if(curPowerHeight > 0)
			{
				g.fillRect(powerBarX, powerBarLowerY-curPowerHeight, Config.POWER_BAR_WIDTH, curPowerHeight);
			}
		}
	}
	
	public String getName()
	{
		return name;
	}
	
	public Rectangle getBoundaries()
	{
		int dir = this.spriteMover.getDirection();
		int rectX = spriteMover.getX();
		int rectY = spriteMover.getY();
		int rectW = curAnimation.getWidth(dir);
		int rectH = curAnimation.getHeight(dir);
		
		return new Rectangle(rectX, rectY, rectW, rectH);
	}
	
	public void setCollided(Sprite hittedObject)
	{
		this.powerMeter.updatePower(- hittedObject.getDamagePower());
		
		if(! isAlive() && curAnimation != dieAnimation)
		{
			curAnimation = dieAnimation;
			
			spriteMover = new NullSpriteMover(spriteMover.getX(), spriteMover.getY(), spriteMover.getDirection());
		}
	}
	
	public boolean isAlive()
	{
		if(powerMeter.getCurPower() > 0)
		{
			return true;
		}
		
		return false;
	}
	
	public boolean isRemovable()
	{
		return (!isAlive()) && curAnimation.isEnded();
	}
	
	public int getDamagePower()
	{
		return powerMeter.getMaxPower();
	}
	
	public int getKillBonus()
	{
		return (int)Math.floor(Math.pow(powerMeter.getMaxPower(), Config.ENEMY_KILLED_BONUS_FACTOR));
	}
	
	public void setParent(Sprite parent)
	{
		this.parent = parent;
	}
	
	public Sprite getParent()
	{
		return parent;
	}
}
