package snake;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class SnakeBody extends GameObject {

	@Override
	public void draw(Graphics g, int x, int y, int width, int height) {
		g.setColor(Color.green);
		g.fillRect(x, y, width, height);
	}

	@Override
	public void update(GameScene scene, Input input, int deltaTime) {}

}
