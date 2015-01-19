package framework;


public interface ISpriteFactory {
	
	public SpriteMover createMover(int x, int y, int direction);
	
	public Animation createMoveAnimation();
	
	public Animation createDieAnimation();
	
	public PowerMeter createPowerMeter();
	
	public String getName();
}
