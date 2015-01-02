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
import br.com.danielhabib.core.CounterClockWiseDirection;
import br.com.danielhabib.core.Environment;
import br.com.danielhabib.core.GoalRule;
import br.com.danielhabib.core.ImageHandler;
import br.com.danielhabib.core.Main2D;
import br.com.danielhabib.core.Position;
import br.com.danielhabib.core.Psico;
import br.com.danielhabib.core.RegularMoveHandler;
import br.com.danielhabib.core.builder.LevelParser;

public abstract class AbstractIntegrationTest {
	protected final int WINDOW_SIZE = Config.SIZE / 2 + Config.SIZE * 6;
	protected JFrame frame;
	protected JApplet applet;
	protected Psico psico;
	protected Environment env;
	protected LevelParser parser;

	@Test
	public void integrationTest() throws Exception {
		frame = buildFrame();

		env = setupEnv();
		psico = setupPsico(psicoInitialPosition());
		applet = new Main2D(psico, env);
		timeout = setTimeoutMillis();

		setupFrame();
		setupCommands();

		setup();
		sleep();
		testIt();
	}

	protected void setup() {
		// Hook to finish setting stuff up.
	}

	protected abstract void testIt() throws Exception;

	protected abstract Position psicoInitialPosition();

	private Psico setupPsico(Position position) {
		moveHandler = new RegularMoveHandler(position, env);
		moveHandler.setRules(parser.getGoalRules());
		return new Psico(new CounterClockWiseDirection(), moveHandler, new ImageHandler());
	}

	protected List<GoalRule> rules() {
		return parser.getGoalRules();
	}

	protected Environment setupEnv() {
		parser = new LevelParser(level());
		Environment env = new Environment();
		env.setBalls(parser.getBalls());
		env.setWalls(parser.getWalls());
		env.setGoals(parser.getGoals());
		return env;
	}

	protected abstract String level();

	protected void setupFrame() {
		frame.getContentPane().add("Center", applet);
		applet.init();
		frame.pack();
		frame.setSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE + Config.SIZE / 2));
		frame.setVisible(true);
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
		JFrame f = new JFrame("Psico");
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		return f;
	}

	protected int timeout = 100;
	protected RegularMoveHandler moveHandler;

	protected abstract int setTimeoutMillis();

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
		TimeUnit.MILLISECONDS.sleep(timeout);
	}
}
