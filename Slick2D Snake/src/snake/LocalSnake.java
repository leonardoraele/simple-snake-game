package snake;

import org.newdawn.slick.Input;

public class LocalSnake extends Snake {
	
	private LocalPlayer player;
	
	public LocalSnake(LocalPlayer player) {
		super();
		this.setup(player);
	}
	
	public LocalSnake(Direction d, LocalPlayer player) {
		super(d);
		this.setup(player);
	}
	
	public LocalSnake(int speed, LocalPlayer player) {
		super(speed);
		this.setup(player);
	}
	
	public LocalSnake(Direction d, int speed, LocalPlayer player) {
		super(d, speed);
		this.setup(player);
	}
	
	private void setup(LocalPlayer player) {
		this.player = player;
	}

	@Override
	public void update(GameScene scene, Input input, int delta) {
		Controller controller = this.player.getController();
		
		if (controller.isButtonPressed(Button.RIGHT)) {
			this.setFace(Direction.RIGHT);
		} else if (controller.isButtonPressed(Button.DOWN)) {
			this.setFace(Direction.DOWN);
		} else if (controller.isButtonPressed(Button.LEFT)) {
			this.setFace(Direction.LEFT);
		} else if (controller.isButtonPressed(Button.UP)) {
			this.setFace(Direction.UP);
		} else if (controller.isButtonPressed(Button.PAUSE) ||
				controller.isButtonPressed(Button.RETURN)) {
			scene.pause();
		}
		
		super.update(scene, input, delta);
	}

}
