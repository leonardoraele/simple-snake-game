package snake;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class PauseScene implements Scene {
	
	private Scene pausedScene;

	public PauseScene(Scene pausedScene) {
		this.pausedScene = pausedScene;
	}

	@Override
	public Scene update(GameContainer gc, int deltaTime) throws SlickException {
		Scene nextScene;

		if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
			nextScene = this.pausedScene;
		} else if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			nextScene = null;
		} else {
			nextScene = this;
		}
		
		return nextScene;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		this.pausedScene.render(gc, g);
		g.drawString("PAUSE", gc.getWidth() / 2, gc.getHeight() / 2);
	}

}
