package framework;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import resources.Config;

public class DefaultScoreManager implements IScoreManager {
	long score = 0;
	int level = 1;
	int killedEnemies = 0;
	
	long curLevelScoreLimit;
	
	int levelChangedMsgCountdown = Config.LEVEL_CHANGED_MESSAGE_DURATION;
	
	
	public DefaultScoreManager()
	{
		curLevelScoreLimit = getLevelScoreLimit(level);
		
		if(Config.DEBUG_MODE_ON)
		{
			System.out.println("Levels score limits:");
			for(int i=0; i<30; i++)
			{
				getLevelScoreLimit(i);
			}
		}
	}
	
	private long getLevelScoreLimit(int lev)
	{
		
		long multiplier = (long)Math.floor(Math.log(lev + 1) / Math.log(2));
		long scoreLimit = (long) (lev * multiplier * Config.LEVEL_UPGRADE_SCORE_STEP);
		
		/*
		int A = 1;
		int B = 0;
		int BASE = 10;
		
		long scoreLimit = (long)(1/12) * (long)(2 * A * lev * (1 - 3 * lev + 2 * Math.pow((double)lev, 2)) + 3 * (400 + 2 * BASE * (-1 + lev) * lev + B * Math.pow((double)(-1 + lev), 2.0) * Math.pow(lev, 2)));
		*/
		
		//System.out.println("Level=" + lev + "\tScoreLimit=" + scoreLimit + "\tmultiplier=" + multiplier);
		if(Config.DEBUG_MODE_ON)
		{
			System.out.println("Level=" + lev + "\tScoreLimit=" + scoreLimit);
		}
		
		return scoreLimit;
	}
	
	private int getLevelFromScore(long score)
	{
		int lev = level;
		
		while(score > getLevelScoreLimit(lev++))
		{	
			if(lev > Config.LEVEL_MAX_LEVEL)
			{
				break;
			}
		}
		
		lev--;
		
		return lev;
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
	
	public void update(long elapsedTime)
	{
		levelChangedMsgCountdown = updateCountdown(levelChangedMsgCountdown, elapsedTime);
		
		if(score > curLevelScoreLimit)
		{
			level = getLevelFromScore(score);
			
			curLevelScoreLimit = getLevelScoreLimit(level);
			
			levelChangedMsgCountdown = Config.LEVEL_CHANGED_MESSAGE_DURATION;
		}
	}
	
	public void draw(Graphics2D g)
	{
		// Level-changed message
		if(levelChangedMsgCountdown > 0)
		{
			g.setColor(Color.ORANGE);
			g.setFont(new Font("Verdana", 2, 40));
			
			g.drawString("Level " + level,
						(int)Math.floor(Config.SCREEN_WIDTH / 2) - 60,
						(int)Math.floor(Config.SCREEN_HEIGHT / 2));
		}
	}
	
	public void enemyHit(int firePower)
	{
		score += firePower;
	}
	
	public void enemyKilled(int killBonus)
	{
		score += killBonus;
		
		killedEnemies++;
	}
	
	
	public int getCurLevel()
	{
		return level;
	}
	
	public long getScore()
	{
		return score;
	}

	public int getKilledEnemies()
	{
		return killedEnemies;
	}
	
	public void stepToNextLevel()
	{
		score = getLevelScoreLimit(level) + 1;
	}
}
