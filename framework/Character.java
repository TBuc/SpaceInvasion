package framework;

import implementation.SFCannonShot;
import implementation.SFDrunkCannonShot;
import implementation.SFLaserShot;

import java.awt.event.KeyEvent;

import resources.Config;
import resources.Resources;



public class Character extends Sprite {
	
	private enum MOVE_DIRECTION {
		STOP,
		LEFT,
		RIGHT
	}
	
	//private MOVE_DIRECTION currentMoveDirection = MOVE_DIRECTION.STOP;
	private boolean leftButtonIsPressed = false;
	private boolean rightButtonIsPressed = false;

	private boolean fireKeyWasReleased = true;
	private long countdownBeforeNextFire;
	
	private int gameLevel = 1;
	
	
	public Character(IGamePlayScreen gamePlay, ISpriteFactory factory, int x, int y, int direction)
	{
		super(gamePlay, factory, x, y, direction);
		
		spriteMover = factory.createMover(x, y, direction);
		
		if(! (spriteMover instanceof CharacterMover))
		{
			System.out.println("Errore: tipo di spriteMover selezionato non compatibile con l'oggetto Character!");
		}
		//nullMover = this.spriteMover;
		//sideMover = new LinearSpriteMover(x, y, 0, 5);
		
		resetFireCountdown();
	}
	
	public void setGameLevel(int level)
	{
		this.gameLevel = level;
	}
	
	public void moveLeft()
	{
		if(isAlive())
		{
			spriteMover.setDirection(Config.SPRITE_DIRECTION_LEFT);
			
			((CharacterMover)spriteMover).setStayStill(false);
		}
	}
	
	public void moveRight()
	{
		if(this.isAlive())
		{
			spriteMover.setDirection(Config.SPRITE_DIRECTION_RIGHT);
			
			((CharacterMover)spriteMover).setStayStill(false);
		}
	}
	
	public void stop()
	{
		if(isAlive())
		{
			((CharacterMover)spriteMover).setStayStill(true);
		}
	}
	
	public void fire()
	{
		if(countdownBeforeNextFire > 0)
		{
			return;
		}
		
		Sprite shot;
		
		int fireDir = spriteMover.getDirection();
		int fireX = spriteMover.getX() + (int)Math.floor(curAnimation.getWidth(fireDir) / 5 * 2);
		int fireY = spriteMover.getY();
		
		
		switch(gameLevel)
		{
		case 1:
			// Single laser shot
			shot = new Sprite(gamePlay, new SFLaserShot(Resources.getInstance()), fireX, fireY, fireDir);
			shot.setParent(this);
			gamePlay.addEntity(shot);
			break;
		case 2:
		case 3:
			// Double laser shot (in-line, 30px distance)
			shot = new Sprite(gamePlay, new SFLaserShot(Resources.getInstance()), fireX, fireY, fireDir);
			shot.setParent(this);
			gamePlay.addEntity(shot);
			
			shot = new Sprite(gamePlay, new SFLaserShot(Resources.getInstance()), fireX, fireY+30, fireDir);
			shot.setParent(this);
			gamePlay.addEntity(shot);
			break;
		case 4:
		case 5:
			// Single cannon shot
			shot = new Sprite(gamePlay, new SFCannonShot(Resources.getInstance()), fireX, fireY, fireDir);
			shot.setParent(this);
			gamePlay.addEntity(shot);
			break;
		case 6:
		case 7:
			// Single drunk cannon shot
			shot = new Sprite(gamePlay, new SFDrunkCannonShot(Resources.getInstance()), fireX, fireY, fireDir);
			shot.setParent(this);
			gamePlay.addEntity(shot);
			break;
		case 8:
		case 9:
		case 10:
			// Laser-shot + drunk cannon shot
			shot = new Sprite(gamePlay, new SFLaserShot(Resources.getInstance()), fireX, fireY, fireDir);
			shot.setParent(this);
			gamePlay.addEntity(shot);

			shot = new Sprite(gamePlay, new SFDrunkCannonShot(Resources.getInstance()), fireX, fireY, fireDir+5);
			shot.setParent(this);
			gamePlay.addEntity(shot);
			break;
		case 11:
		case 12:
		case 13:
		case 14:
			// Double drunk cannon shot, +-10 degrees
			shot = new Sprite(gamePlay, new SFDrunkCannonShot(Resources.getInstance()), fireX, fireY, fireDir-5);
			shot.setParent(this);
			gamePlay.addEntity(shot);
			
			shot = new Sprite(gamePlay, new SFDrunkCannonShot(Resources.getInstance()), fireX, fireY, fireDir+5);
			shot.setParent(this);
			gamePlay.addEntity(shot);
			break;
		default:
			// Triple cannon shot: 2 shots +-10 degrees, and 1 drunk shot.
			shot = new Sprite(gamePlay, new SFCannonShot(Resources.getInstance()), fireX, fireY, fireDir-10);
			shot.setParent(this);
			gamePlay.addEntity(shot);
			
			shot = new Sprite(gamePlay, new SFDrunkCannonShot(Resources.getInstance()), fireX, fireY, fireDir);
			shot.setParent(this);
			gamePlay.addEntity(shot);
			
			shot = new Sprite(gamePlay, new SFCannonShot(Resources.getInstance()), fireX, fireY, fireDir+10);
			shot.setParent(this);
			gamePlay.addEntity(shot);
		}
		
		resetFireCountdown();
	}
	
	public void resetFireCountdown()
	{
		countdownBeforeNextFire = Config.GAME_CHARACTER_FIRE_MIN_INTERVAL;
	}
	
	public void updateMovingBehavior(MOVE_DIRECTION moveDirection)
	{
		switch(moveDirection)
		{
		case LEFT:
			this.moveLeft();
			break;
		case RIGHT:
			this.moveRight();
			break;
		case STOP:
			if(leftButtonIsPressed)
			{
				updateMovingBehavior(MOVE_DIRECTION.LEFT);
			}
			else if(rightButtonIsPressed)
			{
				updateMovingBehavior(MOVE_DIRECTION.RIGHT);
			}
			else
			{
				this.stop();
			}
			break;
		}
	}
	
	@Override
	public void update(long elapsedTime)
	{
		super.update(elapsedTime);
		
		if(countdownBeforeNextFire > 0)
		{
			countdownBeforeNextFire -= elapsedTime;
		}
	}
	
	public void keyPressed(KeyEvent keyEvent)
	{
		int key = keyEvent.getKeyCode();
		
		switch(key)
		{
		case KeyEvent.VK_LEFT:
			leftButtonIsPressed = true;
			updateMovingBehavior(MOVE_DIRECTION.LEFT);
			break;
		case KeyEvent.VK_RIGHT:
			rightButtonIsPressed = true;
			updateMovingBehavior(MOVE_DIRECTION.RIGHT);
			break;
		case KeyEvent.VK_SPACE:
			if(fireKeyWasReleased)
			{
				fireKeyWasReleased = false;
				fire();
			}
			break;
		}
	}
	
	public void keyReleased(KeyEvent keyEvent)
	{
		int key = keyEvent.getKeyCode();
		
		switch(key)
		{
		case KeyEvent.VK_LEFT:
			leftButtonIsPressed = false;
			updateMovingBehavior(MOVE_DIRECTION.STOP);
			break;
		case KeyEvent.VK_RIGHT:
			rightButtonIsPressed = false;
			updateMovingBehavior(MOVE_DIRECTION.STOP);
			break;
		case KeyEvent.VK_SPACE:
			fireKeyWasReleased = true;
			break;
		}
	}
	
	public int getPowerLeft()
	{
		return powerMeter.getCurPower();
	}
}
