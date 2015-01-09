package snake;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class HighscoreScene implements Scene {

	@Override
	public Scene update(GameContainer gc, int deltaTime) throws SlickException {
		// TODO
		return new MainMenuScene(gc);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		// TODO
	}

}
