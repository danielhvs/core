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

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import br.com.danielhabib.core.builder.LevelParser;
import br.com.danielhabib.core.component.Psico;
import br.com.danielhabib.core.gui.Main2D;
import br.com.danielhabib.core.rules.GoalRule;
import br.com.danielhabib.core.rules.GrabbingRules;

public abstract class AbstractIntegrationTest {
	protected static final int CONFIG_SIZE = 64;
	protected static FileSystemXmlApplicationContext context;
	protected final int WINDOW_SIZE = CONFIG_SIZE / 2 + CONFIG_SIZE * 6;
	protected JFrame frame;
	protected JApplet applet;
	protected Psico psico;
	protected LevelParser parser;
	protected int level = 1;
	private int timeout = 10;

	@BeforeClass
	public static void beforeClass() {
		context = new FileSystemXmlApplicationContext(
				"src/main/resources/config/test-beans.xml");
	}

	@Test
	public void integrationTest() throws Exception {
		for (LevelParser levelParser : parsers()) {
			buildFrame();
			parser = levelParser;
			psico = parser.getPsico();
			applet = new Main2D(psico, parser);

			setupFrame();
			setupCommands();

			setup();
			sleep();
			testIt();

			frame.dispose();

			level++;
		}
	}

	protected abstract List<LevelParser> parsers();

	protected void setup() {
		// Hook to finish setting stuff up.
	}

	protected abstract void testIt() throws Exception;

	protected List<GoalRule> rules() {
		return parser.getGoalRules();
	}

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
		return WINDOW_SIZE + CONFIG_SIZE / 2;
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

	protected GrabbingRules moveHandler;

	protected int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	protected void turn90() throws InterruptedException {
		turn();
	}

	protected void turn180() throws InterruptedException {
		turn();
		turn();
	}

	protected void turn270() throws InterruptedException {
		turn();
		turn();
		turn();
	}

	protected void down() throws InterruptedException {
		turn270();
		move();
		turn90();
	}

	protected void up() throws InterruptedException {
		turn90();
		move();
		turn270();
	}

	protected void left() throws InterruptedException {
		turn180();
		move();
		turn180();
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
		if (timeout > 0) {
			TimeUnit.MILLISECONDS.sleep(getTimeout());
		}
	}
}
