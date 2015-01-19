package snake;

public abstract class Player {
	
	private String name;
	
	public Player() {
		this.setName("Player #" + this.hashCode());
	}

	public Player(String name) {
		this.setName(name);
	}
	
	public abstract Snake createSnake();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
