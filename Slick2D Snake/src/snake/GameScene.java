package snake;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class GameScene implements Scene {
	
	private static final int DEFAULT_MAP_WIDTH = 20;
	private static final int DEFAULT_MAP_HEIGHT = 15;
	private static final float CHANCE_TO_SPECIAL_APPLE = 0.05f;
	
	private Map map;
	private Snake playerSnake;
	private int tileWidth;
	private int tileHeight;
	private int score;
	private Scene nextScene;
	private Collection<GameObject> managing;
	
	public GameScene(GameContainer gc) {
		// Setup
		this.map = new Map(DEFAULT_MAP_WIDTH, DEFAULT_MAP_HEIGHT);		// Game map containing the game objects
		this.playerSnake = new Snake(100);								// Creates the player's snake
		this.score = 0;													// Initialize score to 0 (zero)
		this.nextScene = this;											// Scene to return in the next update
		this.managing = new HashSet<GameObject>();						// GameObjects to update and render
		this.tileWidth = gc.getWidth() / this.map.getWidth();			// Width of a tile in pixels
		this.tileHeight = gc.getHeight() / this.map.getHeight();		// Height of a tile in pixels
		
		// Initialize Map
		this.map.addListener(new Map.MapListener() {
			@Override
			public void onRemoved(GameObject object, int fromX, int fromY) {
				GameScene.this.unsign(object);
			}
			@Override
			public void onMoved(GameObject object, int fromX, int fromY) {
				// Do nothing
			}
			@Override
			public void onAdded(GameObject object, int toX, int toY) {
				GameScene.this.sign(object);
			}
		});
		this.map.putPos(0, 0, this.playerSnake);
		this.generateSimpleApple();
	}

	@Override
	public Scene update(GameContainer gc, int deltaTime) throws SlickException {
		this.playerSnake.update(this, gc.getInput(), deltaTime);
		
		if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
			this.pause();
		} else if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			this.gameOver();
		}
		
		return this.nextScene;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		for (GameObject object : this.managing) {
			object.draw(g,
					object.getX() * this.tileWidth,
					object.getY() * this.tileHeight,
					this.tileWidth,
					this.tileHeight
					);
		}
		
		g.setColor(Color.white);
		g.drawString("SCORE: " + this.getScore(), gc.getWidth() / 2, 0);
	}
	
	public Map getMap() {
		return map;
	}
	
	public XY getRandomFreePosition() {
		Random random = new Random();
		int x, y;
		do {
			 x = random.nextInt(this.map.getWidth());
			 y = random.nextInt(this.map.getHeight());
		} while (!this.map.isEmptyPos(x, y));
		return new XY(x, y);
	}
	
	public void generateSimpleApple() {
		Apple apple = new Apple();
		this.generateApple(apple);
		
		Random random = new Random();
		if (random.nextFloat() < CHANCE_TO_SPECIAL_APPLE) {
			switch (random.nextInt(2)) {
			case 0:
				this.generateApple(new BlueApple());
				break;
			case 1:
				this.generateApple(new OrangeApple());
				break;
			}
		}
	}

	private void generateApple(GameObject apple) {
		XY position = this.getRandomFreePosition();
		this.map.putPos(position.x, position.y, apple);
	}

	public void sign(GameObject object) {
		this.managing.add(object);
	}
	
	public void unsign(GameObject object) {
		this.managing.remove(object);
	}

	public void gameOver() {
		this.nextScene = new GameOverScene(this.score);
	}

	public void pause() {
		this.nextScene = new PauseScene(this);
	}

	public void addScore(int scoreIncrease) {
		this.score += scoreIncrease;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}

}
