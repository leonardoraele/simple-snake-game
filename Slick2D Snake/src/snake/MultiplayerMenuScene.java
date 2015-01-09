package snake;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class MultiplayerMenuScene implements Scene {
	
	private String[] options = {"HOST MATCH", "JOIN MATCH", "BACK"};
	private Runnable[] actions = {this::optionHostMatch, this::optionJoinMatch, this::optionBack};
	
	private int index;
	private Scene nextScene;
	private GameContainer gc;
	
	public MultiplayerMenuScene(GameContainer gc) {
		this.gc = gc;
		this.index = 0;
		this.nextScene = this;
	}

	@Override
	public Scene update(GameContainer gc, int deltaTime) throws SlickException {
		if (gc.getInput().isKeyPressed(Input.KEY_UP)) {
			this.index = Math.min(this.index - 1, this.options.length);
		} else if (gc.getInput().isKeyPressed(Input.KEY_DOWN)) {
			this.index = Math.max(this.index + 1, 0);
		} else if (gc.getInput().isKeyPressed(Input.KEY_ENTER) ||
				gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
			this.actions[this.index].run();
		} else if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			this.nextScene = null;
		}
		
		return this.nextScene;
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
	
	public void optionHostMatch() {
		//
		this.options[this.index] = "(PENDING)";
	}
	
	public void optionJoinMatch() {
		// TODO
		this.options[this.index] = "(PENDING)";
	}
	
	public void optionBack() {
		this.nextScene = new MainMenuScene(this.gc);
	}

}
