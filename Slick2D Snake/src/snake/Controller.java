package snake;

/**
 * Not an "MVC-like controller"; this is the abstract representation of
 * something whatever a player uses to to send input to the game. (may be a
 * keyboard, a joystick or a console controller)
 */
public abstract class Controller {
	
	public abstract boolean isButtonDown(Button button);
	public abstract boolean isButtonPressed(Button button);

}
