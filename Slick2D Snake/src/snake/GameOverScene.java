package snake;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class GameOverScene implements Scene {
	
	private int score;
	private boolean showScore;

	public GameOverScene() {
		this.score = 0;
		this.showScore = false;
	}
	
	public GameOverScene(int score) {
		this.score = score;
		this.showScore = true;
		
		if (this.score > HighscoreScene.loadScore()) {
			HighscoreScene.saveScore(this.score);
		}
	}

	@Override
	public Scene update(GameContainer gc, int i) throws SlickException {
		if (gc.getInput().isKeyPressed(Input.KEY_ENTER) ||
			gc.getInput().isKeyPressed(Input.KEY_SPACE)
			) {
			return new MainMenuScene(gc);
		} else if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			return null;
		} else {
			return this;
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setColor(Color.white);
		g.drawString("GAME OVER", gc.getWidth() / 2, gc.getHeight() / 2);
		if (this.showScore) {
			g.drawString("Score: " + this.score, gc.getWidth() / 2, gc.getHeight() / 2 + 32);
		}
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public boolean isDisplayingScore() {
		return showScore;
	}
	
	public void setDisplayScore(boolean showScore) {
		this.showScore = showScore;
	}

}
