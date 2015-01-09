package snake;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Apple extends GameObject implements Snake.SnakeBooster {

	private static final int SCORE_INCREASE = 100;
	private static final int SPEED_INCREASE = 1;

	@Override
	public void draw(Graphics g, int x, int y, int width, int height) {
		g.setColor(Color.red);
		g.fillRect(x, y, width, height);
	}

	@Override
	public void update(GameScene scene, Input input, int deltaTime) {}

	@Override
	public void eaten(Snake snake, GameScene scene) {
		snake.grow();
		snake.setSpeed(snake.getSpeed() - SPEED_INCREASE);
		scene.generateSimpleApple();
		scene.addScore(SCORE_INCREASE);
	}

}
