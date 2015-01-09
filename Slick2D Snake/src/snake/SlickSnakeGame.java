package snake;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class SlickSnakeGame extends BasicGame {
	
	public static void main(String[] args) throws SlickException {
		SlickSnakeGame snakeGame = new SlickSnakeGame();
		AppGameContainer gc = new AppGameContainer(snakeGame, 640, 480, false);
		gc.start();
	}

	private static final String DEFAULT_GAME_NAME = "Snake";
	private static final Class<? extends Scene> MAIN_SCENE_CLASS = MainMenuScene.class;
	
	private Scene scene;
	
	public SlickSnakeGame() {
		super(DEFAULT_GAME_NAME);
	}

	@Override
	public void init(GameContainer gc)
			throws SlickException
	{
		try {
			this.scene = MAIN_SCENE_CLASS.getConstructor(GameContainer.class).newInstance(gc);
		} catch (Exception e) {
			System.err.println("Could not call constructor " + MAIN_SCENE_CLASS.getSimpleName() + "(GameContainer gc) of " + MAIN_SCENE_CLASS);
			e.printStackTrace();
		}
	}

	@Override
	public void update(GameContainer gc, int delta)
			throws SlickException
	{
		this.scene = this.scene.update(gc, delta);
		
		if (this.scene == null) {
			gc.exit();
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g)
			throws SlickException
	{
		this.scene.render(gc, g);
	}

}
