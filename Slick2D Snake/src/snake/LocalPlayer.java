package snake;

public class LocalPlayer extends Player {
	
	private Controller controller;

	public LocalPlayer(String name, Controller controller) {
		super(name);
		this.controller = controller;
	}
	
	public Controller getController() {
		return controller;
	}

	@Override
	public Snake createSnake() {
		return new LocalSnake(this);
	}

}
