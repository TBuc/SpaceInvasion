package framework;

public interface IGamePlayScreen extends IGameScreen {
	
	public void addEntity(Sprite obj);
	
	public int getCurrentLevel();
	
	public boolean isThisSpriteTheCharacter(Sprite s);
}
