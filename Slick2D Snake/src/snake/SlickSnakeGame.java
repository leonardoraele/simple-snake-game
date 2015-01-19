package snake;

import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class SlickSnakeGame extends BasicGame {
	
	private static final String PROPERTIES_FILENAME = "conf.properties";
	
	public static void main(String[] args) throws SlickException, IOException {
		GameConfigs configs = new GameConfigs(PROPERTIES_FILENAME);
		SlickSnakeGame snakeGame = new SlickSnakeGame(configs);
		AppGameContainer gc = new AppGameContainer(snakeGame, 640, 480, false);
		gc.start();
	}
	
	private Scene scene;
	private GameConfigs configs;
	
	public SlickSnakeGame(GameConfigs configs) {
		super(configs.getGameName());
		this.configs = configs;
	}

	@Override
	public void init(GameContainer gc)
			throws SlickException
	{
		try {
			Class<? extends Scene> mainSceneClass = this.configs.getMainSceneClass();
			this.scene = mainSceneClass.getConstructor(GameContainer.class, GameConfigs.class)
					.newInstance(gc, this.configs);
			
			Controller controller = this.configs.createPlayer1Input(gc.getInput());
			Player player = new LocalPlayer("Player #1", controller);
			this.configs.addPlayer(player);
		} catch (Exception e) {
			throw new SlickException("Couldn't instantiate main scene class because " + e.getMessage(), e);
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
		if (this.scene != null) {
			this.scene.render(gc, g);
		}
	}

}
