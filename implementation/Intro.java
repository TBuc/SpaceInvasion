package implementation;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import resources.Config;
import resources.Resources;
import framework.IGameScreen;


public class Intro implements IGameScreen {
	Game game;
	Resources res;
	
	Image bg;
	
	float alpha = 0;
	Integer countdown;
	int x, y;
	
	
	public Intro(Game game, Resources res)
	{
		this.res = res;
		
		this.game = game;
		
		this.bg = res.getIntroBackground();
		this.countdown = Config.GAME_INTRO_DURATION;
	}
	
	public void update(long elapsedTime)
	{
		countdown -= (int) elapsedTime;
		
		if(countdown <= 0)
		{
			goToNextScreen();
		}
		
		updateAlpha(elapsedTime);
	}
	
	private void updateAlpha(long elapsedTime)
	{
		alpha += (float)elapsedTime / (float)Config.GAME_INTRO_DURATION;
		
		if(alpha > 1)
		{
			alpha = 1f;
		}
	}
	
	private void goToNextScreen()
	{
		game.ChangeGameScreen(new GameArena(game, res), this);
	}
	
	
	public void draw(Graphics2D g)
	{
		
		Composite previousComposite = g.getComposite();
		
		Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER , alpha);
		g.setComposite(comp);
		
		
		g.drawImage(bg,
					0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT,
					0, 0, bg.getWidth(null), bg.getHeight(null),
					null);
		
		
		// Restore prev composite (alpha)
		g.setComposite(previousComposite);
		
		
		// Print countdown
		g.setColor(Color.BLUE);
		g.setFont(new Font("Verdana", 1, 34));
		
		int strCountdown = (int)Math.floor(this.countdown / 1000) + 1;
		
		g.drawString(strCountdown + "..",
				(int)Math.floor(Config.SCREEN_WIDTH / 2) - 20,
				(int)Math.floor(Config.GAME_AREA_HEIGHT / 2));
		
	}

	@Override
	public void keyPressed(KeyEvent keyEvent)
	{
		// DO NOTHING
	}

	@Override
	public void keyReleased(KeyEvent keyEvent)
	{
		goToNextScreen();
	}

	@Override
	public void keyTyped(KeyEvent keyEvent)
	{
		// DO NOTHING
	}
	
}
