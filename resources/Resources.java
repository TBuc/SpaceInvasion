package resources;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;


public class Resources {
	
	private static Resources mySingleton = null;
	
	private Resources()
	{
	}
	
	public static synchronized Resources getInstance()
	{
		if(mySingleton == null)
		{
			mySingleton = new Resources();
			mySingleton.init();
		}
		
		return mySingleton;
	}
	
	/*
	 * Static class for images loading
	 */
	static Image loadImage(String fileName) {
    	Image img = null;
    	
    	try
		{	
    		System.out.print("Loading " + fileName + "...");
    		
			img = ImageIO.read(new File(ASSETS_PATH + fileName));
			
			System.out.println(" loaded.");
		} catch (IOException e)
		{
			//TODO return an empty Image to avoid crashes
			System.out.println("Impossibile caricare l'immagine desiderata: " + ASSETS_PATH + fileName);
		}
    	
    	return img;
	}
	

	private void init()
	{
		/*
		 * Intro
		 */
		introBackground = Resources.loadImage(INTRO_BACKGROUND);
		
		/*
		 * Menu
		 */
		/*
		menuItems.add(new MenuItem("Play", 1));
		menuItems.add(new MenuItem("Settings", 2));
		menuItems.add(new MenuItem("Credits", 3));
		menuItems.add(new MenuItem("Exit", 4));
		*/
		
		
		/*
		 * Game Background
		 */
		gameAreaBackground_defaultAnim = new ArrayList<Image>();
		gameAreaBackground_defaultAnim.add(Resources.loadImage("backgroundDefault_01.png"));
		gameAreaBackground_defaultAnim.add(Resources.loadImage("backgroundDefault_02.png"));
		gameAreaBackground_defaultAnim.add(Resources.loadImage("backgroundDefault_03.png"));
		gameAreaBackground_defaultAnim.add(Resources.loadImage("backgroundDefault_04.png"));

		gameAreaBackground_gameOverAnim = new ArrayList<Image>();
		gameAreaBackground_gameOverAnim.add(Resources.loadImage("backgroundDefault_gameOver_01.png"));
		gameAreaBackground_gameOverAnim.add(Resources.loadImage("backgroundDefault_gameOver_02.png"));
		gameAreaBackground_gameOverAnim.add(Resources.loadImage("backgroundDefault_gameOver_03.png"));
		gameAreaBackground_gameOverAnim.add(Resources.loadImage("backgroundDefault_gameOver_04.png"));
		gameAreaBackground_gameOverAnim.add(Resources.loadImage("backgroundDefault_gameOver_05.png"));
		gameAreaBackground_gameOverAnim.add(Resources.loadImage("backgroundDefault_gameOver_06.png"));
		gameAreaBackground_gameOverAnim.add(Resources.loadImage("backgroundDefault_gameOver_07.png"));
		gameAreaBackground_gameOverAnim.add(Resources.loadImage("backgroundDefault_gameOver_08.png"));
		
		
		/*
		 * Character
		 */
		characterDefault_moveAnim = new ArrayList<Image>();
		characterDefault_moveAnim.add(Resources.loadImage("characterDefault_move_01.png"));
		characterDefault_moveAnim.add(Resources.loadImage("characterDefault_move_02.png"));
		characterDefault_moveAnim.add(Resources.loadImage("characterDefault_move_03.png"));
		
		characterDefault_dieAnim = new ArrayList<Image>();
		characterDefault_dieAnim.add(Resources.loadImage("characterDefault_die_01.png"));
		characterDefault_dieAnim.add(Resources.loadImage("characterDefault_die_02.png"));
		characterDefault_dieAnim.add(Resources.loadImage("characterDefault_die_03.png"));
		characterDefault_dieAnim.add(Resources.loadImage("characterDefault_die_04.png"));
		characterDefault_dieAnim.add(Resources.loadImage("characterDefault_die_05.png"));
		characterDefault_dieAnim.add(Resources.loadImage("characterDefault_die_06.png"));
		characterDefault_dieAnim.add(Resources.loadImage("characterDefault_die_07.png"));
		characterDefault_dieAnim.add(Resources.loadImage("characterDefault_die_08.png"));
		characterDefault_dieAnim.add(Resources.loadImage("characterDefault_die_09.png"));

		
		/*
		 *  Weapons - Cannon shot
		 */
		smallCannonShot = Resources.loadImage(SMALL_CANNON_SHOT);
		smallCannonShotDies = Resources.loadImage(SMALL_CANNON_SHOT_DIES);
		
		
		/*
		 *  Weapons - Laser shot
		 */
		shotLaser_moveAnim = new ArrayList<Image>();
		shotLaser_moveAnim.add(Resources.loadImage("laserShot_move_01.png"));
		
		shotLaser_dieAnim= new ArrayList<Image>();
		shotLaser_dieAnim.add(Resources.loadImage("laserShot_die_01.png"));
		
		
		/*
		 *  Spaceship - Space Fighter
		 */
		spaceshipFighter_moveAnim = new ArrayList<Image>();
		spaceshipFighter_moveAnim.add(Resources.loadImage("spaceshipFighter_move_01.png"));
		spaceshipFighter_moveAnim.add(Resources.loadImage("spaceshipFighter_move_02.png"));
		spaceshipFighter_moveAnim.add(Resources.loadImage("spaceshipFighter_move_03.png"));
		
		spaceshipFighter_dieAnim = new ArrayList<Image>();
		spaceshipFighter_dieAnim.add(Resources.loadImage("spaceshipFighter_die_01.png"));
		spaceshipFighter_dieAnim.add(Resources.loadImage("spaceshipFighter_die_02.png"));
		spaceshipFighter_dieAnim.add(Resources.loadImage("spaceshipFighter_die_03.png"));
		spaceshipFighter_dieAnim.add(Resources.loadImage("spaceshipFighter_die_04.png"));
		spaceshipFighter_dieAnim.add(Resources.loadImage("spaceshipFighter_die_05.png"));
		spaceshipFighter_dieAnim.add(Resources.loadImage("spaceshipFighter_die_06.png"));
		
		
		/*
		 *  Spaceship - Space Interceptor
		 */
		spaceshipInterceptor_moveAnim = new ArrayList<Image>();
		spaceshipInterceptor_moveAnim.add(Resources.loadImage("spaceshipInterceptor_move_01.png"));
		spaceshipInterceptor_moveAnim.add(Resources.loadImage("spaceshipInterceptor_move_02.png"));
		spaceshipInterceptor_moveAnim.add(Resources.loadImage("spaceshipInterceptor_move_03.png"));
		spaceshipInterceptor_moveAnim.add(Resources.loadImage("spaceshipInterceptor_move_04.png"));
		
		spaceshipInterceptor_dieAnim = new ArrayList<Image>();
		spaceshipInterceptor_dieAnim.add(Resources.loadImage("spaceshipInterceptor_die_01.png"));
		spaceshipInterceptor_dieAnim.add(Resources.loadImage("spaceshipInterceptor_die_02.png"));
		spaceshipInterceptor_dieAnim.add(Resources.loadImage("spaceshipInterceptor_die_03.png"));
		spaceshipInterceptor_dieAnim.add(Resources.loadImage("spaceshipInterceptor_die_04.png"));
		spaceshipInterceptor_dieAnim.add(Resources.loadImage("spaceshipInterceptor_die_05.png"));
		spaceshipInterceptor_dieAnim.add(Resources.loadImage("spaceshipInterceptor_die_06.png"));
		spaceshipInterceptor_dieAnim.add(Resources.loadImage("spaceshipInterceptor_die_07.png"));
		
		
		/*
		 *  Spaceship - Space Bomber
		 */
		spaceshipBomber_moveAnim = new ArrayList<Image>();
		spaceshipBomber_moveAnim.add(Resources.loadImage("spaceshipBomber_move_01.png"));
		spaceshipBomber_moveAnim.add(Resources.loadImage("spaceshipBomber_move_02.png"));
		spaceshipBomber_moveAnim.add(Resources.loadImage("spaceshipBomber_move_03.png"));
		spaceshipBomber_moveAnim.add(Resources.loadImage("spaceshipBomber_move_04.png"));
		
		spaceshipBomber_dieAnim = new ArrayList<Image>();
		spaceshipBomber_dieAnim.add(Resources.loadImage("spaceshipBomber_die_01.png"));
		spaceshipBomber_dieAnim.add(Resources.loadImage("spaceshipBomber_die_02.png"));
		spaceshipBomber_dieAnim.add(Resources.loadImage("spaceshipBomber_die_03.png"));
		

		/*
		 *  Planet - Mercury
		 */
		planetMercury_moveAnim = new ArrayList<Image>();
		planetMercury_moveAnim.add(Resources.loadImage("planetMercury_move_01.png"));
		planetMercury_moveAnim.add(Resources.loadImage("planetMercury_move_02.png"));
		planetMercury_moveAnim.add(Resources.loadImage("planetMercury_move_03.png"));
		
		planetMercury_dieAnim = new ArrayList<Image>();
		planetMercury_dieAnim.add(Resources.loadImage("planetMercury_die_01.png"));
		planetMercury_dieAnim.add(Resources.loadImage("planetMercury_die_02.png"));
		planetMercury_dieAnim.add(Resources.loadImage("planetMercury_die_03.png"));
		planetMercury_dieAnim.add(Resources.loadImage("planetMercury_die_04.png"));
		planetMercury_dieAnim.add(Resources.loadImage("planetMercury_die_05.png"));
		planetMercury_dieAnim.add(Resources.loadImage("planetMercury_die_06.png"));
		planetMercury_dieAnim.add(Resources.loadImage("planetMercury_die_07.png"));
		planetMercury_dieAnim.add(Resources.loadImage("planetMercury_die_08.png"));
		planetMercury_dieAnim.add(Resources.loadImage("planetMercury_die_09.png"));
		planetMercury_dieAnim.add(Resources.loadImage("planetMercury_die_10.png"));
		planetMercury_dieAnim.add(Resources.loadImage("planetMercury_die_11.png"));
		planetMercury_dieAnim.add(Resources.loadImage("planetMercury_die_12.png"));
		planetMercury_dieAnim.add(Resources.loadImage("planetMercury_die_13.png"));
		
		
	}
	
	
	/*
	 * GLOBAL SETTINGS
	 */
	private static final String ASSETS_PATH = "assets/";
	
	public final Random random = new Random(System.currentTimeMillis());
	public final int SHOT_LAUNCH_TIMER_ENTROPY = 50;	//Milliseconds
	
	/*
	 * MENU
	 */	
	//TODO: List<MenuItem> menuItems;
	
	public final String MAIN_MENU_SINGLE_PLAYER = "menu_play.png";
	public final String MAIN_MENU_MULTIPLE_PLAYER = "menu_play.png";
	public final String MAIN_MENU_CREDITS = "menu_credits.png";
	
	
	/*
	 * INTRO
	 */
	private Image introBackground;
	private final String INTRO_BACKGROUND = "intro.png";
	

	/*
	 * GAME ARENA BACKGROUND
	 */
	private List<Image> gameAreaBackground_defaultAnim;
	private List<Image> gameAreaBackground_gameOverAnim;
	
	
	/*
	 * CHARACTER
	 */
	private List<Image> characterDefault_moveAnim;
	private List<Image> characterDefault_dieAnim;
	public final String CHARACTER_DEFAULT_NAME = "Star Fighter";
	public final int CHARACTER_DEFAULT_INITIAL_POWER = 4;
	public final int CHARACTER_DEFAULT_SPEED = 60;
	
	
	/*
	 * WEAPONS SHOTS
	 */
	
	// Laser-shot
	private List <Image> shotLaser_moveAnim;
	private List<Image> shotLaser_dieAnim;
	public final String LASER_SHOT_NAME = "Fighter";
	public final int LASER_SHOT_INITIAL_POWER = 1;
	public final int LASER_SHOT_SPEED = 55;
	
	// Cannon-shot
	private Image smallCannonShot;
	private Image smallCannonShotDies;
	private final String SMALL_CANNON_SHOT = "small_cannon_shot.png";
	private final String SMALL_CANNON_SHOT_DIES = "small_cannon_shot_dies.png";
	public final int SMALL_CANNON_SHOT_INITIAL_POWER = 2;
	public final int SMALL_CANNON_SHOT_SPEED = 40;
	public final String SMALL_CANNON_SHOT_NAME = "Small Cannon Shot";
	
	// Drunk cannon-shot: inherites cannonshot's properties, except for:
	public final int DRUNK_CANNON_SHOT_INITIAL_POWER = 3;
	public final int DRUNK_CANNON_SHOT_SPEED = 50;
	

	/*
	 * ENEMIES
	 */
	
	// Battleships (star-fighters)
	private List <Image> spaceshipFighter_moveAnim;
	private List<Image> spaceshipFighter_dieAnim;
	public final String FIGHTER_SPACESHIP_NAME = "Fighter";
	public final int FIGHTER_SPACESHIP_INITIAL_POWER = 11;
	public final int FIGHTER_SPACESHIP_SPEED = 13;
	
	private List <Image> spaceshipInterceptor_moveAnim;
	private List<Image> spaceshipInterceptor_dieAnim;
	public final String INTERCEPTOR_SPACESHIP_NAME = "Interceptor";
	public final int INTERCEPTOR_SPACESHIP_INITIAL_POWER = 5;
	public final int INTERCEPTOR_SPACESHIP_SPEED = 16;
	
	private List <Image> spaceshipBomber_moveAnim;
	private List<Image> spaceshipBomber_dieAnim;
	public final String BOMBER_SPACESHIP_NAME = "Bomber";
	public final int BOMBER_SPACESHIP_INITIAL_POWER = 22;
	public final int BOMBER_SPACESHIP_SPEED = 8;
	
	// Planets
	private List <Image> planetMercury_moveAnim;
	private List<Image> planetMercury_dieAnim;
	public final String MERCURY_PLANET_NAME = "Mercury";
	public final int MERCURY_PLANET_INITIAL_POWER = 150;
	public final int MERCURY_PLANET_SPEED = 2;
	
	
	
	/*
	 * GETTERS
	 */
	
	// Intro
	public Image getIntroBackground()
	{
		return introBackground;
	}
	
	// Menu
	/*
	public List<MenuItem> getMenuItemsEnum()
	{
		return java.util.Collections.unmodifiableList(menuItems);
	}
	*/
	
	// Game Play
	public List<Image> getGameAreaBackground_defaultAnim()
	{
		return gameAreaBackground_defaultAnim;
	}
	
	public List<Image> getGameAreaBackground_gameOverAnim()
	{
		return gameAreaBackground_gameOverAnim;
	}
	
	public Image getSmallCannonShot()
	{
		return smallCannonShot;
	}
	
	public Image getSmallCannonShotDies()
	{
		return smallCannonShotDies;
	}
	
	// Default Character
	public List<Image> getCharacterDefaultMoveAnim()
	{
		return this.characterDefault_moveAnim;
	}
	
	public List<Image> getCharacterDefaultDieAnim()
	{
		return this.characterDefault_dieAnim;
	}
	
	// Laser shot
	public List<Image> getShotLaserMoveAnim()
	{
		return this.shotLaser_moveAnim;
	}
	
	public List<Image> getShotLaserDieAnim()
	{
		return this.shotLaser_dieAnim;
	}
	
	// Fighter
	public List<Image> getFighterSpaceShipMoveAnim()
	{
		return this.spaceshipFighter_moveAnim;
	}

	public List<Image> getFighterSpaceShipDieAnim()
	{
		return this.spaceshipFighter_dieAnim;
	}
	
	// Interceptor
	public List<Image> getInterceptorSpaceShipMoveAnim()
	{
		return this.spaceshipInterceptor_moveAnim;
	}
	
	public List<Image> getInterceptorSpaceShipDieAnim()
	{
		return this.spaceshipInterceptor_dieAnim;
	}
	
	// Bomber
	public List<Image> getBomberSpaceShipMoveAnim()
	{
		return this.spaceshipBomber_moveAnim;
	}
	
	public List<Image> getBomberSpaceShipDieAnim()
	{
		return this.spaceshipBomber_dieAnim;
	}

	// Planet - Mercury
	public List<Image> getPlanetMercury_moveAnim()
	{
		return this.planetMercury_moveAnim;
	}
	
	public List<Image> getPlanetMercury_dieAnim()
	{
		return this.planetMercury_dieAnim;
	}
	
}

