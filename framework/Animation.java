package framework;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import resources.Config;


public class Animation {
	private List<Image> frames;
	private int curFrame = 0;
	private int prevFrame = 0;
	
	private long frameDuration = Config.ANIM_DEFAULT_FRAME_DURATION;
	private long curFrameElapsedTime = 0;	// Milliseconds
	
	private long fadingDuration = Config.ANIM_DEFAULT_FADING_DURATION;
	private float prevFrameAlpha = 1f;
	
	private boolean loop;
	private boolean fading;
	private boolean isEnded = false;
	
	private int height = 0;
	private int width = 0;
	
	
	public Animation(boolean loop, boolean fading)
	{
		frames = new ArrayList<Image>();
		this.loop = loop;
		this.fading = fading;
	}
	
	public void addFrame(Image img)
	{
		//TODO add check for all images same size as first, otherwise don't load them (problems with collisions detection)
		frames.add(img);
		
		if(frames.size() == 1)
		{
			height = img.getHeight(null);
			width = img.getWidth(null);
		}
	}
	
	public void setFrameDuration(long frameDuration)
	{
		this.frameDuration = frameDuration;
		
		checkFadingDurationConsistence();
	}
	
	public void setFadingDuration(long fadingDuration)
	{
		this.fadingDuration = fadingDuration;
		
		checkFadingDurationConsistence();
	}
	
	private void checkFadingDurationConsistence()
	{
		if(fading)
		{
			if(fadingDuration > frameDuration)
			{
				fadingDuration = frameDuration;
			}
		}
	}
	
	public boolean isEnded()
	{
		return isEnded;
	}
	
	private boolean isRotated(int direction)
	{
		direction = direction % 360;
		
		if( (direction > 45 && direction < 135) ||
			(direction > 225 && direction < 315) )
		{
			return true;
		}
		
		return false;
	}
	
	public int getHeight(int direction)
	{
		if(isRotated(direction))
		{
			return width;
		}
		else
		{
			return height;
		}
	}
	
	public int getWidth(int direction)
	{
		if(isRotated(direction))
		{
			return height;
		}
		else
		{
			return width;
		}
	}
	
	public void update(long elapsedTime)
	{
		/*
		 * If Animation is ended or no frame is set, I skip updating
		 */
		if(	isEnded ||
			frames.size() == 0)
		{
			return;
		}
		
		
		/*
		 * If frame duration is over, I call frame update procedure
		 */
		curFrameElapsedTime += elapsedTime;
		
		if(curFrameElapsedTime >= frameDuration)
		{
			curFrameElapsedTime = 0;
			prevFrame = curFrame;
			prevFrameAlpha = 1f;
			curFrame++;
			
			//System.out.println("prevFrame=" + prevFrame + "\tcurFrame=" + curFrame + "\tfading=" + fading + "\tprevFrameAlpha=" + prevFrameAlpha);
			
			/*
			 * If last frame:
			 * - loop==true:  go back to first frame
			 * - loop==false: end animation
			 */
			if(curFrame == frames.size())
			{
				if(loop)
				{
					curFrame = 0;
				}
				else
				{
					isEnded = true;
					curFrame--;
				}
			}
		}
		
		
		/*
		 * If fading is set on, I update fading value
		 */
		if(fading && prevFrameAlpha > 0)
		{
			prevFrameAlpha -= (float)elapsedTime / fadingDuration;
			
			if(prevFrameAlpha < 0)
			{
				prevFrameAlpha = 0;
			}
		}
		
	}
	
	
	public void draw(Graphics2D g, int x, int y, int direction)
	{
		Image img = frames.get(curFrame);
		
		// Warning: transforms are applied in reverse order!! (I labeled them: 1-2-3-4)
		AffineTransform tx = new AffineTransform();
		
		// 4 - Translate image to its current position on the screen
		tx.translate(x, y);
		
		if(direction != 0)
		{
			// 3 - Translate image back to match its coords with its bottom-left corner
			// Use new image direction.
			tx.translate( this.getWidth(direction)/2, this.getHeight(direction)/2 );
			
			// 2 - Rotate image to match current direction
			tx.rotate(Math.toRadians(360 - direction));
			
			// 1 - Translate image by width/2 and height/2, so that rotation is centered.
			// Use PNGs default direction.
			tx.translate( -this.getWidth(0)/2, -this.getHeight(0)/2 );
		}
		
		
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		/*
		Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f - prevFrameAlpha);
		g.setComposite(comp);
		*/
		
		g.drawImage(img, tx, null);
		
		if(fading == true && prevFrameAlpha > 0)
		{
			Image prevImg = frames.get(prevFrame);
			
			Composite previousComposite = g.getComposite();
			Composite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, prevFrameAlpha);
			
			g.setComposite(alphaComposite);
			g.drawImage(prevImg, tx, null);
			
			// Restore previous alpha settings
			g.setComposite(previousComposite);
		}
		
		//g.setColor(Color.white);
		//g.drawString(""+curFrame, x, y);
	}
}
