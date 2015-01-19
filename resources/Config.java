package resources;


public class Config {
	
	public static final String TITLE = "Space Invasion - by Antonio Bucciol";
	
	public static final boolean DEBUG_MODE_ON = false;
	
	// Screen & main loop settings
	public static final long THREAD_WAIT_TIME_BEFORE_EXIT = 2000;	//millis
	public static final int BUFFERS_AMOUNT = 2;	// DON'T TOUCH THIS: only double buffer is supported.
	public static final int MAIN_LOOP_CYCLE_DURATION = 30;
	
	// Screen size
	// and game area partitioning
	public static final int GAME_STATUS_BAR_HEIGHT = 60;
	
	public static final int SCREEN_WIDTH = 500;
	public static final int SCREEN_HEIGHT = 600 + GAME_STATUS_BAR_HEIGHT;
	
	
	public static final int GAME_AREA_X = 0;
	public static final int GAME_AREA_Y = 0;
	public static final int GAME_AREA_WIDTH = SCREEN_WIDTH;
	public static final int GAME_AREA_HEIGHT = SCREEN_HEIGHT - GAME_STATUS_BAR_HEIGHT;

	public static final int GAME_STATUS_BAR_WIDTH = SCREEN_WIDTH;
	public static final int GAME_STATUS_BAR_X = 0;
	public static final int GAME_STATUS_BAR_Y = GAME_AREA_HEIGHT;
	
	// Character position on screen
	private static final int GAME_AREA_CHARACTER_DISTANCE_FROM_BASE = 80;
	public static final int GAME_AREA_CHARACTER_POSITION_Y = GAME_AREA_HEIGHT - GAME_AREA_CHARACTER_DISTANCE_FROM_BASE;
	public static final int GAME_CHARACTER_DIRECTION = 90;
	
	// Intro
	public static final int GAME_INTRO_DURATION = 2500;		//Milliseconds
	
	// Character
	public static final int GAME_CHARACTER_INITIAL_LIVES = 3;
	public static final int GAME_CHARACTER_FIRE_MIN_INTERVAL = 150;	// Millis
	public static final int GAME_CHARACTER_IMMUNITY_LAPSE = 1700;	// Millis
	
	// Enemies
	public static final int GAME_ENEMIES_LAUNCH_WAIT_LAPSE = 2300; // Millis
	public static final int GAME_ENEMIES_LAUNCH_WAIT_LAPSE_RND_MAX_ADDITION = 400; // Millis
	public static final int GAME_ENEMIES_LAUNCH_WAIT_LEVEL_DECREASER = 15; // Millis
	
	// Animations
	public static final int ANIM_DEFAULT_FRAME_DURATION = 70;		// Millis
	public static final int ANIM_DEFAULT_FADING_DURATION = 40;		// Millis
	
	// Sprites
	public static final int SPRITE_DIRECTION_RIGHT = 0;
	public static final int SPRITE_DIRECTION_LEFT = SPRITE_DIRECTION_RIGHT + 180;
	public static final int SPRITE_NORMAL_SHIP_MAX_WIDTH = 64;	// Max width of a 'normal' ship (not 'final enemies')
	public static final int POWER_BAR_WIDTH = 4;
	
	// Score	
	public static final double ENEMY_KILLED_BONUS_FACTOR = 1.3;
	public static final double LEVEL_UPGRADE_SCORE_STEP = 100;
	public static final int LEVEL_MAX_LEVEL = 400;
	
	// Others
	public static final int LEVEL_CHANGED_MESSAGE_DURATION = 2500;
}
