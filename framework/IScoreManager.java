package framework;

import java.awt.Graphics2D;

public interface IScoreManager {

	public void update(long elapsedTime);
	
	public void draw(Graphics2D g);
	
	public void enemyHit(int firePower);
	
	public void enemyKilled(int killBonus);
	
	public int getCurLevel();
	
	public long getScore();

	public int getKilledEnemies();

	public void stepToNextLevel();
	
}
