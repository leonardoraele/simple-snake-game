package snake;

import java.util.Deque;
import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public abstract class Snake extends GameObject {
	
	public interface SnakeBooster {
		public void eaten(Snake snake, GameScene scene);
	}
	
	private static final int MIN_SPEED = 10;
	private static final Direction DEFAULT_DIRECTION = Direction.RIGHT;
	private static final int DEFAULT_SPEED = 100;
	
	private Direction face;
	private int speed;
	private int deltaCount;
	private Deque<SnakeBody> segments;
	
	public Snake() {
		this(DEFAULT_DIRECTION, DEFAULT_SPEED);
	}
	
	public Snake(Direction d) {
		this(d, DEFAULT_SPEED);
	}
	
	public Snake(int speed) {
		this(DEFAULT_DIRECTION, speed);
	}
	
	public Snake(Direction d, int speed) {
		this.face = d;
		this.speed = speed;
		this.deltaCount = 0;
		this.segments = new LinkedList<SnakeBody>();
	}
	
	public Direction getFace() {
		return face;
	}
	
	public void setFace(Direction face) {
		this.face = face;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int speed) {
		this.speed = Math.max(MIN_SPEED, speed);
	}
	
	@Override
	public void update(GameScene scene, Input input, int delta) {
		this.deltaCount += delta;
		
		while (this.deltaCount >= this.speed) {
			Map map = scene.getMap();
			
			// Important for knowing where to move the segments after moving the head
			int fromX = this.getX();
			int fromY = this.getY();
			
			// Moves snake's head (this)
			GameObject collision = map.moveRelativeOrLoop(
					this, this.face.getX(), this.face.getY());
			
			// Moves the segments
			if (!this.segments.isEmpty()) {
				SnakeBody segment = this.segments.removeLast();
				if (segment.hasPosition()) {
					map.moveAbsolute(segment, fromX, fromY);
				} else {
					map.putPos(fromX, fromY, segment);
				}
				this.segments.addFirst(segment);
			}
			
			// Checks for collisions
			if (collision instanceof SnakeBooster) {
				SnakeBooster booster = (SnakeBooster) collision;
				booster.eaten(this, scene);
			} else if (collision != null) {
				scene.gameOver();
			}
			
			// Reset movement delay
			this.deltaCount -= speed;
		}
	}

	@Override
	public void draw(Graphics g, int x, int y, int width, int height) {
		g.setColor(Color.green);
		g.fillRect(x, y, width, height);
	}

	public void grow() {
		this.segments.add(new SnakeBody());
	}

}
