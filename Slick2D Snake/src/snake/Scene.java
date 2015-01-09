package snake;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public interface Scene {
	
	public Scene update(GameContainer gc, int deltaTime)
			throws SlickException;
	
	public void render(GameContainer gc, Graphics g)
			throws SlickException;
	
}
