package snake;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Input;

public class KeyboardController extends Controller {
	
	public Map<Button, Integer> inputMap;
	private Input input;
	
	public KeyboardController(Input input) {
		this.inputMap = new HashMap<Button, Integer>(Button.values().length);
		this.input = input;
	}

	public KeyboardController(Input input, Map<Button, Integer> inputMap) {
		if (inputMap.values().contains(null)) {
			throw new IllegalArgumentException("A button cannot be mapped to null.");
		}
		this.inputMap = inputMap;
		this.input = input;
	}
	
	public void setKey(Button button, int key) {
		this.inputMap.put(button, key);
	}

	public int getKey(Button button) {
		return this.inputMap.get(button);
	}

	@Override
	public boolean isButtonDown(Button button) {
		Integer key = this.inputMap.get(button);
		return this.input.isKeyDown(key);
	}

	@Override
	public boolean isButtonPressed(Button button) {
		Integer key = this.inputMap.get(button);
		return this.input.isKeyPressed(key);
	}

}
