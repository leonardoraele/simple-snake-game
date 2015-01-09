package snake;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class BlueApple extends GameObject implements Snake.SnakeBooster {
	
	private static final int SPEED_DECREASE = 25;
	private static final int SCORE_INCREASE = 300;
	private static final int TIME_TO_VANISH = 4000;
	private static final int FRAMES_TO_BLINK = 15;
	
	private int timeCount;
	private int blinkCount;
	
	public BlueApple() {
		this.timeCount = 0;
		this.blinkCount = FRAMES_TO_BLINK;
	}

	@Override
	public void eaten(Snake snake, GameScene scene) {
		snake.grow();
		snake.setSpeed(snake.getSpeed() + SPEED_DECREASE);
		scene.addScore(SCORE_INCREASE);
	}

	@Override
	public void draw(Graphics g, int x, int y, int width, int height) {
		this.blinkCount--;
		if (this.blinkCount == 0) {
			this.blinkCount = FRAMES_TO_BLINK;
		} else {
			g.setColor(Color.blue);
			g.fillRect(x, y, width, height);
		}
	}

	@Override
	public void update(GameScene scene, Input input, int deltaTime) {
		this.timeCount += deltaTime;
		
		if (this.timeCount >= TIME_TO_VANISH) {
			scene.getMap().remove(this);
		}
	}

}
