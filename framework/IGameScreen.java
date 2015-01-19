package framework;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;


public interface IGameScreen {
	
	public void update(long elapsedTime);
	
	public void draw(Graphics2D g);
	
	public void keyPressed(KeyEvent keyEvent);
	
	public void keyReleased(KeyEvent keyEvent);

	public void keyTyped(KeyEvent keyEvent);
}
