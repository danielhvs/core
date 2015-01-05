package br.com.danielhabib.core;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.apache.commons.io.FileUtils;

import br.com.danielhabib.core.builder.LevelParser;

public class App {
	protected static final int WINDOW_SIZE = Config.SIZE / 2 + Config.SIZE * 6;
	protected static JFrame frame;
	protected static JApplet applet;
	protected static Psico psico;
	private static LevelParser parser;
	private static List<LevelParser> parsers;
	private static int level = 0;

	public static void main(String[] args) throws InterruptedException, IOException {
		String level1 = FileUtils.readFileToString(new File("level_1.txt"));
		String level2 = FileUtils.readFileToString(new File("level_1.txt"));

		parsers = new LevelHandler(new String[] { level1, level2 }).getParsers();
		nextLevel();
	}

	protected static void nextLevel() {
		if (lastLevelIsDone()) {
			gameOver();
		} else {
			displayNextLevel();
		}
	}

	private static boolean lastLevelIsDone() {
		return level == parsers.size();
	}

	private static void displayNextLevel() {
		frame = buildFrame();
		parser = parsers.get(level++);
		psico = parser.getPsico();
		parser.setMoveHandlerObserver(new IRulesObserver() {
			public void levelIsOver() throws Exception {
				frame.dispose();
				nextLevel();
			}
		});

		applet = new Main2D(psico, parser.getEnv());
		setupFrame();
		setupCommands();
	}

	private static void gameOver() {
		// Whe're done!
	}

	protected static void setupFrame() {
		frame.getContentPane().add("Center", applet);
		applet.init();
		frame.pack();
		frame.setSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE + Config.SIZE / 2));
		frame.setVisible(true);
	}

	protected static void setupCommands() {
		frame.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				char keyChar = e.getKeyChar();
				switch (keyChar) {
				case 'f':
					psico.move();
					break;
				case 'd':
					psico.turn();
					break;
				case 'g':
					psico.grab();
					break;
				case 'b':
					psico.drop();
					break;
				case 'q':
					System.exit(0);
					break;
				}
			}
		});
	}

	protected static JFrame buildFrame() {
		JFrame f = new JFrame("Psico");
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		return f;
	}
}
