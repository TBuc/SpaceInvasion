package implementation;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import resources.Config;
import resources.Resources;
import framework.Animation;
import framework.Character;
import framework.DefaultScoreManager;
import framework.IGamePlayScreen;
import framework.IScoreManager;
import framework.Sprite;


public class GameArena implements IGamePlayScreen {
	Game game;
	Resources res;
	
	Character character;
	ArrayList<Sprite> entities = new ArrayList<Sprite>();
	HashMap<Sprite,Sprite> collidedEntities = new HashMap<Sprite, Sprite>();
	
	Animation curBgAnim;
	Animation bgPlayAnim;
	Animation bgDieAnim;
	
	IScoreManager scoreManager = new DefaultScoreManager();

	boolean paused = false;
	
	int livesLeft = Config.GAME_CHARACTER_INITIAL_LIVES;
	//int curLevel = 1;
	//long score = 0;
	
	int characterImmunityCountdown = 0;
	int enemyLaunchCountdown = 0;
	
	
	public GameArena(Game game, Resources res) {
		this.res = res;
		this.game = game;
		
		// Load background play animation
		bgPlayAnim = new Animation(true, true);
		bgPlayAnim.setFrameDuration(5000);
		bgPlayAnim.setFadingDuration(3000);
		
		List<Image> bgFrames = res.getGameAreaBackground_defaultAnim();
		
		for(int i=0; i<bgFrames.size(); i++)
		{
			bgPlayAnim.addFrame(bgFrames.get(i));
		}
		
		// Load background die animation
		bgDieAnim = new Animation(false, true);
		bgDieAnim.setFrameDuration(250);
		bgDieAnim.setFadingDuration(200);
		
		List<Image> xxx = res.getGameAreaBackground_gameOverAnim();

		for(int i=0; i<xxx.size(); i++)
		{
			bgDieAnim.addFrame(xxx.get(i));
		}
		
		// Set current bg animation
		curBgAnim = bgPlayAnim;
		
		// Load character
		loadCharacter();
	}
	
	
	private void loadCharacter()
	{
		if(livesLeft > 0)
		{
			//character = new Sprite(this, new CharacterDefault(res), res.GAME_AREA_WIDTH / 2, res.GAME_AREA_CHARACTER_POSITION_Y, 90);
			character = new Character(	this,
										new SFCharacterDefault(res),
										Config.GAME_AREA_WIDTH / 2,
										Config.GAME_AREA_CHARACTER_POSITION_Y,
										Config.GAME_CHARACTER_DIRECTION);
			
			character.setGameLevel(scoreManager.getCurLevel());
			
			characterImmunityCountdown = Config.GAME_CHARACTER_IMMUNITY_LAPSE;
		}
		else
		{
			if(Config.DEBUG_MODE_ON)
			{
				System.out.println("Game over");
			}
			
			curBgAnim = bgDieAnim;
		}
	}
	
	
	public boolean isThisSpriteTheCharacter(Sprite s)
	{
		if(s == character)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/*
	 * Check for collisions between game objects
	 */
	public void checkCollisions()
	{
		/*
		 * Check for character vs entities() collisions
		 */
		if(characterImmunityCountdown == 0)
		{
			for(int i=0; i<entities.size(); i++)
			{
				Sprite obj = entities.get(i);
				
				if(obj.getBoundaries().intersects(character.getBoundaries())
					&& obj.isAlive()
					&& character.isAlive())
				{
					//System.out.println("INTERSECTS!");
					
					if(obj.getParent() != character
						&& !alreadyCollided(character, obj))
					{
						setCollided(obj, character);
					}
				}
			}
		}
		
		/*
		 * Check for infra-entities collisions
		 */
		for(int i=0; i<entities.size(); i++)
		{
			Sprite obj1 = entities.get(i);
			
			if(!obj1.isAlive())
			{
				continue;
			}
			
			for(int j=i+1; j<entities.size(); j++)
			{
				Sprite obj2 = entities.get(j);
				
				if(!obj2.isAlive())
				{
					continue;
				}
				
				if( obj1.getParent() == obj2 ||
					obj2.getParent() == obj1 ||
					obj1.getParent() == obj2.getParent())
				{
					continue;
				}
				
				if(alreadyCollided(obj1, obj2))
				{
					continue;
				}
				
				if(obj1.getBoundaries().intersects(obj2.getBoundaries()))
				{
					//System.out.println("INTERSECTS:\t" + obj1.getName() + " <-> " + obj2.getName());
					
					setCollided(obj1, obj2);
					
					if(obj1.getParent() == character)
					{
						scoreManager.enemyHit(obj1.getDamagePower());
						
						if(!obj2.isAlive())
						{
							scoreManager.enemyKilled(obj2.getKillBonus());
						}
					}
					
					if(obj2.getParent() == character)
					{
						scoreManager.enemyHit(obj2.getDamagePower());
						
						if(!obj1.isAlive())
						{
							scoreManager.enemyKilled(obj1.getKillBonus());
						}
					}
					
				}
			}
		}
	}
	
	/*
	 * add entity
	 */
	public void addEntity(Sprite obj)
	{
		this.entities.add(obj);
	}
	
	
	private void unSetCollided(Sprite obj)
	{
		try
		{
			collidedEntities.remove(obj);
			
			if(collidedEntities.containsValue(obj))
			{
				for (Map.Entry<Sprite, Sprite> entry : collidedEntities.entrySet()) {
					if(obj.equals(entry.getValue())) {
						collidedEntities.remove(entry.getKey());
					}
				}
			}
		}
		catch(ConcurrentModificationException e)
		{
			// Do nothing
		}
	}
	
	private void setCollided(Sprite obj1, Sprite obj2)
	{
		if(//!alreadyCollided(obj1, obj2) &&
			obj1 != obj2)
		{
			collidedEntities.put(obj1, obj2);
			
			obj1.setCollided(obj2);
			obj2.setCollided(obj1);
		}
	}
	
	private boolean alreadyCollided(Sprite obj1, Sprite obj2)
	{
		if(collidedEntities.containsKey(obj1))
		{
			if(collidedEntities.get(obj1).equals(obj2))
			{
				return true;
			}
		}
		
		if(collidedEntities.containsKey(obj2))
		{
			if(collidedEntities.get(obj2).equals(obj1))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/*
	 * Removes entities that are dead
	 */
	private void removeDeadEntities()
	{
		Rectangle gameArea = new Rectangle(0, 0, Config.GAME_AREA_WIDTH, Config.GAME_AREA_HEIGHT);
		
		for(int i=0; i<entities.size(); i++)
		{
			Sprite obj = entities.get(i);
			
			if( obj.isRemovable() ||
				!gameArea.intersects(obj.getBoundaries()))
			{
				entities.remove(obj);
				unSetCollided(obj);
				//System.out.println("Object removed. #Entities=" + entities.size());
			}
		}
	}
	
	
	private int updateCountdown(int countdown, long elapsedTime)
	{
		if(countdown > 0)
		{
			countdown -= elapsedTime;
			
			if(countdown < 0)
			{
				countdown = 0;
			}
		}
		
		return countdown;
	}
	
	
	public void update(long elapsedTime) {
		
		if(paused)
		{
			return;
		}
		
		enemyLaunchCountdown = updateCountdown(enemyLaunchCountdown, elapsedTime);
		characterImmunityCountdown = updateCountdown(characterImmunityCountdown, elapsedTime);
		
		
		if(enemyLaunchCountdown == 0)
		{
			enemyLaunchCountdown = Config.GAME_ENEMIES_LAUNCH_WAIT_LAPSE - scoreManager.getCurLevel() * Config.GAME_ENEMIES_LAUNCH_WAIT_LEVEL_DECREASER + res.random.nextInt(Config.GAME_ENEMIES_LAUNCH_WAIT_LAPSE_RND_MAX_ADDITION);
			
			launchEnemy();
		}
		
		
		/*
		 * Update bg animation
		 */
		curBgAnim.update(elapsedTime);
		
		if(livesLeft == 0)
		{
			if(curBgAnim.isEnded())
			{
				game.RemoveGameScreen(this);
			}
			
			return;
		}
		
		/*
		 * Update character and entities
		 */
		character.update(elapsedTime);
		
		for(int i=0; i<entities.size(); i++)
		{
			Sprite obj = entities.get(i);
			
			obj.update(elapsedTime);
		}
		
		
		/*
		 * Check for collisions / dead entities
		 */
		checkCollisions();
		
		removeDeadEntities();

		scoreManager.update(elapsedTime);
		
		character.setGameLevel(scoreManager.getCurLevel());
		
		if(character.isRemovable())
		{
			livesLeft--;
			
			unSetCollided(character);
			character = null;
			
			loadCharacter();
		}
		
	}
	
	private void launchEnemy()
	{
		int startPos = res.random.nextInt(Config.GAME_AREA_WIDTH - Config.SPRITE_NORMAL_SHIP_MAX_WIDTH);
		
		// Max value of 'case'
		int maxEnemyAvailable = 6;
		
		int maxEnemyLaunchable = scoreManager.getCurLevel();
		
		if(maxEnemyLaunchable > maxEnemyAvailable)
		{
			maxEnemyLaunchable = maxEnemyAvailable;
		}
		
		int rnd = res.random.nextInt(maxEnemyLaunchable+1);
		
		switch(rnd)
		{
		case 0:
		case 1:
			addEntity(new Sprite(this, new SFInterceptorSpaceShip(res, scoreManager.getCurLevel()), startPos, 1, 270));
			break;
		case 2:
		case 3:
			addEntity(new Sprite(this, new SFFighterSpaceShip(res, scoreManager.getCurLevel()), startPos, 1, 270));
			break;
		case 4:
		case 5:
			addEntity(new Sprite(this, new SFBomberSpaceShip(res, scoreManager.getCurLevel()), startPos, 1, 270));
			break;
		case 6:  
			if(res.random.nextInt(8) == 0)
			{
				addEntity(new Sprite(this, new SFMercuryPlanet(res, scoreManager.getCurLevel()), startPos, 1, 270));
			}
		}
		
	}
	
	public void draw(Graphics2D g) {
		
		g.setColor(Color.white);
		g.fillRect(0, 0, game.getWidth(), game.getHeight());
		
		curBgAnim.draw(g, 0, 0, 0);
		
		if(livesLeft == 0)
		{
			g.setColor(Color.RED);
			g.setFont(new Font("Verdana", 1, 26));
			
			g.drawString("Game over",
						(int)Math.floor(Config.GAME_AREA_WIDTH / 2) - 30,
						(int)Math.floor(Config.GAME_AREA_HEIGHT / 2));
			return;
		}
		
		for(int i=0; i<entities.size(); i++)
		{
			Sprite obj = entities.get(i);
			
			obj.draw(g);
			
			if(!obj.isAlive()
				&& obj.getParent() != character)
			{
				Rectangle r = obj.getBoundaries();
				int strKillX = r.x + (int)Math.floor(r.width / 5 * 4);
				int strKillY = r.y + (int)Math.floor(r.height / 5);

				g.setColor(Color.WHITE);
				g.setFont(new Font("Verdana", 1, 8));
				
				g.drawString("+" + obj.getKillBonus(), strKillX, strKillY);
			}
		}
		
		character.draw(g);
		
		scoreManager.draw(g);
		
		/*
		 *  Info-bar
		 */
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(Config.GAME_STATUS_BAR_X, Config.GAME_STATUS_BAR_Y, Config.GAME_STATUS_BAR_WIDTH, Config.GAME_STATUS_BAR_HEIGHT);
		int strX = Config.GAME_STATUS_BAR_X + 10;
		int strY = Config.GAME_STATUS_BAR_Y;
		int strStepY = 20;
		
		// Left-side of bar
		g.setColor(Color.BLACK);
		g.setFont(new Font("Verdana", 1, 10));

		strY += strStepY;
		g.drawString("Score: " + scoreManager.getScore(), strX, strY);

		strY += strStepY;
		g.drawString("Enemies KO: " + scoreManager.getKilledEnemies(), strX, strY);
		
		// Center of bar
		g.setColor(Color.BLACK);
		g.setFont(new Font("Verdana", 1, 12));
		strX = Config.GAME_STATUS_BAR_X + (int)Math.floor(Config.GAME_STATUS_BAR_WIDTH / 2 - 50);
		strY = Config.GAME_STATUS_BAR_Y;
		
		strY += strStepY;
		g.drawString("Level: " + scoreManager.getCurLevel(), strX, strY);
		
		strY += strStepY;
		g.drawString("Score: " + scoreManager.getScore(), strX, strY);
		
		// Right-side of bar
		strX = Config.GAME_STATUS_BAR_X + Config.GAME_STATUS_BAR_WIDTH - 100;
		strY = Config.GAME_STATUS_BAR_Y;
		
		g.setFont(new Font("Verdana", 1, 12));

		strY += strStepY;
		g.drawString("Power: " + character.getPowerLeft(), strX, strY);
		
		strY += strStepY;
		g.drawString("Lives: " + livesLeft, strX, strY);
		

		if(paused)
		{
			g.setColor(Color.RED);
			g.setFont(new Font("Verdana", 1, 26));
			
			g.drawString("GAME PAUSED - Press P",
						(int)Math.floor(Config.GAME_AREA_WIDTH / 4),
						(int)Math.floor(Config.GAME_AREA_HEIGHT / 2));
		}
		
	}
	
	@Override
	public int getCurrentLevel()
	{
		return scoreManager.getCurLevel();
	}
	
	
	@Override
	public void keyPressed(KeyEvent keyEvent)
	{
		if(character != null)
		{
			character.keyPressed(keyEvent);
		}
		
		int key = keyEvent.getKeyCode();
		
		switch(key)
		{
		case KeyEvent.VK_P:
			paused = !paused;
			break;
		case KeyEvent.VK_N:
			scoreManager.stepToNextLevel();
			break;
		}
		
		if(keyEvent.getKeyCode() == KeyEvent.VK_P)
		{
			paused = !paused;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent keyEvent)
	{
		if(character != null)
		{
			character.keyReleased(keyEvent);
		}
	}
	
	@Override
	public void keyTyped(KeyEvent keyEvent)
	{
		// Do nothing
	}
	
}
