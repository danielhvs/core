package br.com.danielhabib.core;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

import br.com.danielhabib.core.builder.LevelParser;
import br.com.danielhabib.core.component.LevelHandler;
import br.com.danielhabib.core.component.Psico;
import br.com.danielhabib.core.gui.Main2D;
import br.com.danielhabib.core.rules.IRulesObserver;

@Component
public class App {
	protected static JFrame frame;
	protected static JApplet applet;
	protected static Psico psico;
	private static LevelParser parser;
	private static List<LevelParser> parsers;
	private static int level = 0;

	private static int windowHeight;
	public void setWindowHeight(int windowHeight) {
		App.windowHeight = windowHeight;
	}

	private static int windowWidth;
	public void setWindowWidth(int windowWidth) {
		App.windowWidth = windowWidth;
	}

	private LevelHandler levelHandler;
	public void setLevelHandler(LevelHandler levelHandler) {
		this.levelHandler = levelHandler;
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/config/beans.xml");
		App app = context.getBean(App.class);
		app.start(args);
	}

	private void start(String[] args) throws BeansException, IOException {
		parsers = levelHandler.getParsers();
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
		System.out.println("Game over!");
		// We're done!
	}

	protected static void setupFrame() {
		frame.getContentPane().add("Center", applet);
		applet.init();
		frame.pack();
		frame.setSize(new Dimension(App.windowWidth, App.windowHeight));
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

	protected static void down() {
		turn();
		turn();
		turn();
		turn();
		turn();
		turn();
		move();
		turn();
		turn();
	}

	protected static void up() {
		turn();
		turn();
		move();
		turn();
		turn();
		turn();
		turn();
		turn();
		turn();
	}

	protected static void left() {
		turn();
		turn();
		turn();
		turn();
		move();
		turn();
		turn();
		turn();
		turn();
	}

	protected void drop() {
		psico.drop();
		sleep();
	}

	protected void grab() {
		psico.grab();
		sleep();
	}

	protected static void move() {
		psico.move();
		sleep();
	}

	protected static void turn() {
		psico.turn();
		sleep();
	}

	protected static void sleep() {
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			// omission
		}
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
