package br.com.danielhabib.integration.core;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.junit.Test;

import br.com.danielhabib.core.Config;
import br.com.danielhabib.core.Environment;
import br.com.danielhabib.core.GoalRule;
import br.com.danielhabib.core.LevelHandler;
import br.com.danielhabib.core.Main2D;
import br.com.danielhabib.core.Psico;
import br.com.danielhabib.core.RegularMoveHandler;
import br.com.danielhabib.core.builder.LevelParser;

public abstract class AbstractIntegrationTest {
	protected final int WINDOW_SIZE = Config.SIZE / 2 + Config.SIZE * 6;
	protected JFrame frame;
	protected JApplet applet;
	protected Psico psico;
	protected LevelParser parser;
	protected int level = 1;

	@Test
	public void integrationTest() throws Exception {
		List<LevelParser> parsers = new LevelHandler(levels()).getParsers();
		for (LevelParser levelParser : parsers) {
			buildFrame();
			parser = levelParser;
			psico = parser.getPsico();
			applet = new Main2D(psico, parser.getEnv());

			setupFrame();
			setupCommands();

			setup();
			sleep();
			testIt();

			frame.dispose();

			level++;
		}
	}

	protected void setup() {
		// Hook to finish setting stuff up.
	}

	protected abstract void testIt() throws Exception;

	protected List<GoalRule> rules() {
		return parser.getGoalRules();
	}

	protected Environment setupEnv(String level) {
		parser = new LevelParser(level);
		return parser.getEnv();
	}

	protected abstract List<String> levels();

	protected void setupFrame() {
		frame.getContentPane().add("Center", applet);
		applet.init();
		frame.pack();
		frame.setSize(new Dimension(xWindowSize(), yWindowSize()));
		frame.setVisible(true);
	}

	protected int xWindowSize() {
		return WINDOW_SIZE;
	}

	protected int yWindowSize() {
		return WINDOW_SIZE + Config.SIZE / 2;
	}

	protected void setupCommands() {
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
				// System.out.println("DEBUG: speed = " + speed +
				// ". Position = " + psico.getPosition().toString());
			}
		});
	}

	protected JFrame buildFrame() {
		frame = new JFrame("Psico");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		return frame;
	}

	protected RegularMoveHandler moveHandler;

	protected abstract int timeoutMillis();

	protected void down() throws InterruptedException {
		turn();
		turn();
		turn();
		move();
		turn();
	}

	protected void up() throws InterruptedException {
		turn();
		move();
		turn();
		turn();
		turn();
	}

	protected void left() throws InterruptedException {
		turn();
		turn();
		move();
		turn();
		turn();
	}

	protected void drop() throws InterruptedException {
		psico.drop();
		sleep();
	}

	protected void grab() throws InterruptedException {
		psico.grab();
		sleep();
	}

	protected void move() throws InterruptedException {
		psico.move();
		sleep();
	}

	protected void turn() throws InterruptedException {
		psico.turn();
		sleep();
	}

	protected void sleep() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(timeoutMillis());
	}
}
