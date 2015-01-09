package snake;

import java.util.LinkedList;
import java.util.List;

public class Map {
	
	public interface MapListener {
		public void onAdded(GameObject object, int toX, int toY);
		public void onRemoved(GameObject object, int fromX, int fromY);
		public void onMoved(GameObject object, int fromX, int fromY);
	}

	private static final int DEFAULT_WIDTH = 20;
	private static final int DEFAULT_HEIGHT = 20;
	private static final String EXCEPTION_OUTSIDE_OF_MAP = "Coordenate (%d, %d) is outside of the map.";

	private GameObject[] map;
	private int width;
	private int height;
	private List<MapListener> listeners;
	
	public Map() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public Map(int width, int height) {
		this.width = width;
		this.height = height;
		this.map = new GameObject[this.width * this.height];
		this.listeners = new LinkedList<MapListener>();
	}
	
	public void addListener(MapListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeListener(MapListener listener) {
		this.listeners.remove(listener);
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}

	public boolean isEmptyPos(int x, int y) {
		return this.peekPos(x, y) == null;
	}

	public void putPos(int x, int y, GameObject object)
		throws IllegalArgumentException
	{
		if (!inbounds(x, y)) {
			throw new IllegalArgumentException(String.format(EXCEPTION_OUTSIDE_OF_MAP, x, y));
		}
		
		this.map[this.width * y + x] = object;
		object.setX(x);
		object.setY(y);
		
		this.listeners.forEach(l -> l.onAdded(object, x, y));
	}
	
	private boolean inbounds(int x, int y) {
		return x >= 0 && x < this.width && y >= 0 && y < this.height;
	}

	public GameObject peekPos(int x, int y)
			throws IllegalArgumentException
	{
		if (!inbounds(x, y)) {
			throw new IllegalArgumentException(String.format(EXCEPTION_OUTSIDE_OF_MAP, x, y));
		}
		return this.map[this.width * y + x];
	}
	
	public GameObject popPos(int x, int y)
			throws IllegalArgumentException
	{
		if (!inbounds(x, y)) {
			throw new IllegalArgumentException(String.format(EXCEPTION_OUTSIDE_OF_MAP, x, y));
		}
		GameObject result = this.map[this.width * y + x];
		this.map[this.width * y + x] = null;
		if (result != null) {
			result.removePosition();
		}
		
		this.listeners.forEach(l -> l.onRemoved(result, x, y));
		return result;
	}
	
	public void remove(GameObject object) {
		this.popPos(object.getX(), object.getY());
	}
	
	public GameObject moveAbsolute(GameObject object, int x, int y)
			throws IllegalArgumentException
	{
		if (!inbounds(x, y)) {
			throw new IllegalArgumentException(String.format(EXCEPTION_OUTSIDE_OF_MAP, x, y));
		}
		int fromX = object.getX();
		int fromY = object.getY();
		
		GameObject result = this.popPos(x, y);
		this.remove(object);
		this.putPos(x, y, object);
		
		this.listeners.forEach(l -> l.onMoved(object, fromX, fromY));
		return result;
	}
	
	public GameObject moveRelativeOrLoop(GameObject object, int x, int y) {
		int toX = (object.getX() + x + this.width) % this.width;
		int toY = (object.getY() + y + this.height) % this.height;
		
		return this.moveAbsolute(object, toX, toY);
	}
	
	public GameObject moveRelativeOrThrow(GameObject object, int x, int y) {
		int toX = object.getX() + x;
		int toY = object.getY() + y;
		
		if (!inbounds(toX, toY)) {
			throw new IllegalArgumentException(String.format(EXCEPTION_OUTSIDE_OF_MAP, toX, toY));
		}
		
		return this.moveAbsolute(object, toX, toY);
	}
	
	public GameObject moveAny(int fromX, int fromY, int toX, int toY)
			throws IllegalArgumentException
	{
		if (!inbounds(fromX, fromY)) {
			throw new IllegalArgumentException(String.format(EXCEPTION_OUTSIDE_OF_MAP, fromX, fromY));
		} else if (!inbounds(toX, toY)) {
			throw new IllegalArgumentException(String.format(EXCEPTION_OUTSIDE_OF_MAP, toX, toY));
		}
		GameObject moving = this.popPos(fromX, fromY);
		GameObject result = this.popPos(toX, toY);
		this.putPos(toX, toY, moving);
		
		this.listeners.forEach(l -> l.onMoved(moving, fromX, fromY));
		return result;
	}

}
