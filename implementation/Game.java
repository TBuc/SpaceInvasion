package implementation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import resources.Config;
import resources.Resources;
import framework.IGameScreen;


@SuppressWarnings("serial")
public class Game extends Frame implements Runnable, KeyListener {
	
	Resources res;
	BufferStrategy strategy;
	
	Boolean isRunning = false;
	boolean screenChangeRequired = false;
	
	ArrayList<IGameScreen> gameScreens = new ArrayList<IGameScreen>();
	IGameScreen currentScreen;
	
	Integer fps = 0;
	
	
	// add some system property control flags. Setting these flags as "True"
	// with a capital will notify you via the console if the particular
	// pipeline D3D or Opengl has been successfully enabled. Using "true" all
	// lowercase will turn it on silently, you can still find out if it was
	// successful using the trace property as it will show calls to either D3D
	// or opengl, or neither.
	//
	// NOTE: trace does not seem to work for opengl with jdk1.6.0_20
	static {
		
		//System.setProperty("sun.java2d.trace", "timestamp,log,count");
		//System.setProperty("sun.java2d.transaccel", "True");
		//System.setProperty("sun.java2d.opengl", "True");
		
		// System.setProperty("sun.java2d.d3d", "false"); //default on windows
		// System.setProperty("sun.java2d.ddforcevram", "true");
	}
	
	
	public Game(Resources res) {
		super(Config.TITLE);
		
		this.res = res;
		
		init();
		
		this.AddGameScreen(new Intro(this, res));
	}
	
	
	/**
	Exits the VM from a daemon thread. The daemon thread waits
	2 seconds then calls System.exit(0). Since the VM should
	exit when only daemon threads are running, this makes sure
	System.exit(0) is only called if neccesary. It's neccesary
	if the Java Sound system is running.
	*/
	public void lazilyExit() {
		Thread thread = new Thread() {
			public void run() {
				// first, wait for the VM exit on its own.
				try {
					Thread.sleep(Config.THREAD_WAIT_TIME_BEFORE_EXIT);
				}
				catch (InterruptedException ex) { }
				// system is still running, so force an exit
				System.exit(0);
			}
		};
		
		thread.setDaemon(true);
		thread.start();
	}
	
	
	/*
	 * Init game
	 */
	public void init() {
		this.setIgnoreRepaint(true);
		this.setLayout(null);
		this.setSize(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
		this.setLocationRelativeTo(null); // centers window on screen
		this.setVisible(true);
		
		this.addKeyListener(this);
		
		this.createBufferStrategy(Config.BUFFERS_AMOUNT);
		
		// get a reference to the strategy object, for use in our render method
		// this isn't necessary but it eliminates a call during rendering.
		this.strategy = this.getBufferStrategy();
		
		/*
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					stop(); // first stop the drawing and updating
					setVisible(false); // hide the window quickly
					dispose(); // release all system resources
					System.exit(0); // finally exit.
				}
			}
		});
		*/
		
		
		// need this to trap when the user attempts to close the window using
		// the close icon for the window, or the close option from the window
		// menu or alt+f4 or by other means.
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				stop(); // first stop the drawing and updating
				setVisible(false); // hide the window quickly
				dispose(); // release all system resources
				System.exit(0); // finally exit.
			}
		});
		
	
	}
	
	/**
	Signals the game loop that it's time to quit
	*/
	public void stop() {
		isRunning = false;
	}
	
	
	public void run() {
		isRunning = true;
		
		long startTime = System.currentTimeMillis();
		long currTime = startTime;
		int curCycleFps = 0;
		long fpsTimer = 0;
		
		try {
			 while (isRunning) {
			  	long elapsedTime = System.currentTimeMillis() - currTime;
				currTime += elapsedTime;
				fpsTimer += elapsedTime;
				
				// update
				update(elapsedTime);
				
				// draw the screen
				//Graphics2D g = (Graphics2D)this.getGraphics();
				Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
				
				draw(g);
				g.dispose();
				
				this.strategy.show();
				
				Toolkit.getDefaultToolkit().sync();
				
				if(fpsTimer >= 1000)
				{
					fpsTimer = 0;
					fps = curCycleFps;
					curCycleFps = 0;
					
					//System.out.println("Main loop duration: " + elapsedTime + " FPS=" + fps);
				} else
				{
					curCycleFps++;
				}
				
				if(elapsedTime < Config.MAIN_LOOP_CYCLE_DURATION)
					Thread.sleep(Config.MAIN_LOOP_CYCLE_DURATION - elapsedTime);
				
			 }
		} catch(InterruptedException ex)
		{
			ex.printStackTrace();
		} catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally {
			//screen.restoreScreen();
			lazilyExit();
		}
	}
	
	
	public void AddGameScreen(IGameScreen gsNew) {
		this.gameScreens.add(gsNew);
		this.currentScreen = gsNew;
	}
	
	
	public void RemoveGameScreen(IGameScreen gsOld) {
		if(this.gameScreens.size() > 1)
		{
			currentScreen = gameScreens.get(this.gameScreens.size()-2);
			
			gameScreens.remove(gsOld);
		}
		else
		{
			ChangeGameScreen(new Intro(this, res), gsOld);
		}
	}
	
	
	//this.gameScreens.get(this.gameScreens.size()-1).draw(g);
	public void ChangeGameScreen(IGameScreen gsNew, IGameScreen gsOld) {
		this.gameScreens.add(gsNew);
		this.currentScreen = gsNew;
		
		this.gameScreens.remove(gsOld);
	}
	
	
	public void update(long elapsedTime) {
		// Update current screen
		currentScreen.update(elapsedTime);
		
		if(screenChangeRequired)
		{
			screenChangeRequired = false;
		}
	}
	
	public void draw(Graphics2D g) {
		g.setBackground(new Color(100, 120, 120));
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		// Draw current screen
	
		currentScreen.draw(g);
		
		// Print FPS
		g.setColor(Color.BLUE);
		g.setFont(new Font("Verdana", 1, 10));
		g.drawString("FPS: " + fps.toString(),
					Config.GAME_AREA_WIDTH + 5,
					50);
		
		if(screenChangeRequired)
		{
			screenChangeRequired = false;
		}
	}
	
	
	@Override
	public void keyPressed(KeyEvent keyEvent)
	{
		if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
			stop(); // first stop the drawing and updating
			setVisible(false); // hide the window quickly
			dispose(); // release all system resources
			System.exit(0); // finally exit.
		}
		
		
		currentScreen.keyPressed(keyEvent);
	}
	
	
	@Override
	public void keyReleased(KeyEvent keyEvent)
	{
		currentScreen.keyReleased(keyEvent);
	}
	
	
	@Override
	public void keyTyped(KeyEvent keyEvent)
	{
		currentScreen.keyTyped(keyEvent);
	}
}

