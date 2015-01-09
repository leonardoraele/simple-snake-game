package snake;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class HighscoreScene implements Scene {
	
	public static final String HIGHSCORE_FILENAME = "highscore.txt";
	private Integer highscore;

	public HighscoreScene() {
		this.highscore = HighscoreScene.loadScore();
	}

	@Override
	public Scene update(GameContainer gc, int deltaTime) throws SlickException {
		if (gc.getInput().isKeyPressed(Input.KEY_ENTER) ||
			gc.getInput().isKeyPressed(Input.KEY_SPACE) ||
			gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			return new MainMenuScene(gc);
		} else {
			return this;
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if (this.highscore != null) {
			g.setColor(Color.white);
			g.drawString("Highest score was " + this.highscore, gc.getWidth() / 2, gc.getHeight() / 2);
		} else {
			g.setColor(Color.red);
			g.drawString("ERROR: Highscore could not be loaded.", gc.getWidth() / 2, gc.getHeight() / 2);
		}
	}

	public static Integer loadScore() {
		Integer result;
		try {
			FileInputStream input = new FileInputStream(HIGHSCORE_FILENAME);
			Scanner scanner = new Scanner(input);
			result = scanner.nextInt();
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	public static void saveScore(int score) {
		try {
			FileOutputStream output = new FileOutputStream(HighscoreScene.HIGHSCORE_FILENAME);
			PrintWriter writer = new PrintWriter(output);
			writer.println(""+score);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
