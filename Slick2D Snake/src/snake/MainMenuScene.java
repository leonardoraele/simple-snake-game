package snake;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class MainMenuScene implements Scene {
	
	private interface GetScene {
		public Scene getScene();
	}
	
	private String[] options = {"START", "MULTIPLAYER", "HIGHSCORE", "EXIT"};
	private GetScene[] actions = {this::startScene, this::multiplayerScene, this::highscoreScene, this::exitScene};
	
	private int index;
	private GameContainer gc;

	public MainMenuScene(GameContainer gc) {
		this.gc = gc;
		this.index = 0;
	}

	@Override
	public Scene update(GameContainer gc, int i) throws SlickException {
		if (gc.getInput().isKeyPressed(Input.KEY_UP)) {
			this.index = Math.max(this.index - 1, 0);
		} else if (gc.getInput().isKeyPressed(Input.KEY_DOWN)) {
			this.index = Math.min(this.index + 1, this.options.length - 1);
		} else if (gc.getInput().isKeyPressed(Input.KEY_ENTER) ||
				gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
			return this.actions[this.index].getScene();
		} else if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			return null;
		}
		
		return this;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setColor(Color.white);
		g.drawString("SUPER SNAKE RAELE FTW", gc.getWidth() / 2, gc.getHeight() / 2 - 48);
		for (int i = 0; i < this.options.length; i++) {
			String text = this.options[i];
			if (this.index == i) {
				text = "> " + text + " <";
			}
			g.drawString(text, gc.getWidth() / 2, gc.getHeight() / 2 + 24 * i);
		}
	}
	
	public Scene startScene() {
		return new GameScene(this.gc);
	}
	
	public Scene multiplayerScene() {
		return new MultiplayerMenuScene(this.gc);
	}
	
	public Scene highscoreScene() {
		return new HighscoreScene();
	}
	
	public Scene exitScene() {
		return null;
	}

}
