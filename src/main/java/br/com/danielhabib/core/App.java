package br.com.danielhabib.core;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JApplet;
import javax.swing.JFrame;

import br.com.danielhabib.core.builder.LevelParser;

public class App {
	protected static final int WINDOW_SIZE = Config.SIZE / 2 + Config.SIZE * 6;
	protected static JFrame frame;
	protected static JApplet applet;
	protected static Psico psico;
	private static LevelParser parser;

	public static void main(String[] args) throws InterruptedException, IOException {
		frame = buildFrame();

		Environment env = newEnv();
		RegularMoveHandler moveHandler = new RegularMoveHandler(new Position(Config.SIZE, Config.SIZE * 4), env);
		moveHandler.setRules(parser.getGoalRules());
		moveHandler.setObserver(new IRulesObserver() {
			public void levelIsOver() {
				System.out.println("levelIsOver!!!");
			}
		});
		psico = new Psico(new CounterClockWiseDirection(), moveHandler, new ImageHandler());
		applet = new Main2D(psico, env);

		setupFrame();
		setupCommands();
	}

	private static Environment newEnv() throws IOException {
		parser = new LevelParser(new File("level_1.txt"));
		Environment env = new Environment();
		env.setBalls(parser.getBalls());
		env.setWalls(parser.getWalls());
		env.setGoals(parser.getGoals());
		return env;
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
				//System.out.println("DEBUG: speed = " + speed + ". Position = " + psico.getPosition().toString());
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
