package snake;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.newdawn.slick.Input;

public class GameConfigs {

	private static final String PROPERTY_GAME_NAME = "game_name";
	private static final String PROPERTY_MAIN_SCENE = "game_main_scene";

	private static final String PROPERTY_MAIN_INPUT = "input_main";
//	private static final String PROPERTY_PLAYER_1_INPUT = "input_player_1";
//	private static final String PROPERTY_PLAYER_2_INPUT = "input_player_2";
//	private static final String PROPERTY_PLAYER_3_INPUT = "input_player_3";
//	private static final String PROPERTY_PLAYER_4_INPUT = "input_player_4";
	
	private Properties properties;
	private List<Player> playerConfigs;

	public GameConfigs(Properties properties) {
		this.setup(properties);
	}

	public GameConfigs(String propertiesFilename) throws IOException {
		FileInputStream input = new FileInputStream(propertiesFilename);
		
		Properties properties = new Properties();
		properties.load(input);
		
		input.close();
		
		this.setup(properties);
	}

	private void setup(Properties properties) {
		this.properties = properties;
		this.playerConfigs = new ArrayList<Player>();
	}
	
	public KeyboardController createPlayer1Input(Input input) {
		String propertyName = this.properties.getProperty(PROPERTY_MAIN_INPUT);
		Map<Button, Integer> inputMap = new HashMap<Button, Integer>(4);
		
		try {
			String[] values = this.properties.getProperty(propertyName).split(",");
			
			String right = values[0]; if (!right.isEmpty())
			inputMap.put(Button.RIGHT, Input.class.getField(right).getInt(null));
			
			String down = values[1]; if (!down.isEmpty())
			inputMap.put(Button.DOWN, Input.class.getField(down).getInt(null));
			
			String left = values[2]; if (!left.isEmpty())
			inputMap.put(Button.LEFT, Input.class.getField(left).getInt(null));
			
			String up = values[3]; if (!up.isEmpty())
			inputMap.put(Button.UP, Input.class.getField(up).getInt(null));
			
			String pause = values[4]; if (!pause.isEmpty())
			inputMap.put(Button.PAUSE, Input.class.getField(pause).getInt(null));
			
			String accept = values[5]; if (!accept.isEmpty())
			inputMap.put(Button.ACCEPT, Input.class.getField(accept).getInt(null));
			
			String _return = values[6]; if (!_return.isEmpty())
			inputMap.put(Button.RETURN, Input.class.getField(_return).getInt(null));
		} catch (Exception e) {
			throw new RuntimeException("Uma tecla especificada não é um campo válido da classe Input.", e);
		}
		
		return new KeyboardController(input, inputMap);
	}

	public List<Player> getPlayers() {
		return this.playerConfigs;
	}

	public String getGameName() {
		return this.properties.getProperty(PROPERTY_GAME_NAME);
	}

	public Class<? extends Scene> getMainSceneClass() throws ClassNotFoundException, ClassCastException {
		String mainSceneClassName = this.properties.getProperty(PROPERTY_MAIN_SCENE);
		@SuppressWarnings("unchecked") Class<? extends Scene> mainSceneClass =
				(Class<? extends Scene>) Class.forName(mainSceneClassName);
		return mainSceneClass;
	}

	public void addPlayer(Player player) {
		this.playerConfigs.add(player);
	}

}
