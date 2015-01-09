package snake;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public abstract class GameObject {
	
	private Integer x;
	private Integer y;

	public abstract void draw(Graphics g, int x, int y, int width, int height);
	public abstract void update(GameScene scene, Input input, int deltaTime);

	public int getX() {
		return x;
	}

	void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	void setY(int y) {
		this.y = y;
	}
	
	void setPosition(int x, int y) {
		this.setX(x);
		this.setY(y);
	}
	
	void removePosition() {
		this.x = null;
		this.y = null;
	}
	
	public boolean hasPosition() {
		return this.x != null && this.y != null;
	}

}
