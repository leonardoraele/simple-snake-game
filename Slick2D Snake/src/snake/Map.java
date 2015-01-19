package snake;

import java.util.LinkedList;
import java.util.List;

public class Map {
	
	public interface MapListener {
		public void onAdded(GameObject object, GameObject collision);
		public void onRemoved(GameObject object, int fromX, int fromY);
		public void onMoved(GameObject object, int fromX, int fromY, GameObject collision);
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

	public GameObject putPos(int x, int y, GameObject object)
		throws IllegalArgumentException
	{
		if (!inbounds(x, y)) {
			throw new IllegalArgumentException(String.format(EXCEPTION_OUTSIDE_OF_MAP, x, y));
		}
		
		GameObject result = this.map[this.width * y + x];
		if (result != null) {
			result.removePosition();
		}
		
		this.map[this.width * y + x] = object;
		object.setPosition(x, y);

		this.listeners.forEach(l -> l.onRemoved(result, x, y));
		this.listeners.forEach(l -> l.onAdded(object, result));
		
		return result;
	}

	public GameObject peekPos(int x, int y)
			throws IllegalArgumentException
	{
		if (!inbounds(x, y)) {
			throw new IllegalArgumentException(String.format(EXCEPTION_OUTSIDE_OF_MAP, x, y));
		}
		return this.map[this.width * y + x];
	}

	public GameObject peekPosOrLoop(int x, int y)
			throws IllegalArgumentException
	{
		return this.peekPos(loopX(x), loopY(y));
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
		
		// Tire-o de onde estava
		if (object.hasPosition()) {
			this.map[this.width * object.getY() + object.getX()] = null;
		}
		
		// Remova o que está na posição destino
		GameObject result = this.map[this.width * y + x];
		this.map[this.width * y + x] = null;
		if (result != null) {
			result.removePosition();
		}
		
		// Coloque-o na posição destino
		this.map[this.width * y + x] = object;
		object.setPosition(x, y);

		this.listeners.forEach(l -> l.onRemoved(result, fromX, fromY));
		this.listeners.forEach(l -> l.onMoved(object, fromX, fromY, result));
		
		return result;
	}
	
	public GameObject moveRelativeOrLoop(GameObject object, int x, int y) {
		int toX = loopX(object.getX() + x);
		int toY = loopY(object.getY() + y);
		
		return this.moveAbsolute(object, toX, toY);
	}
	
	private int loopX(int x) {
		return (x + this.width) % this.width;
	}
	
	private int loopY(int y) {
		return (y + this.height) % this.height;
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
		return this.moveAbsolute(this.peekPos(fromX, fromY), toX, toY);
	}
	
	private boolean inbounds(int x, int y) {
		return x >= 0 && x < this.width && y >= 0 && y < this.height;
	}

}
