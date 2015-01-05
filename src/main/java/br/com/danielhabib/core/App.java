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

		parser = new LevelParser(new File("level_1.txt"));
		psico = parser.getPsico();
		parser.setMoveHandlerObserver(new IRulesObserver() {
			public void levelIsOver() {
				System.out.println("levelIsOver!!!");
			}
		});
		applet = new Main2D(psico, parser.getEnv());

		setupFrame();
		setupCommands();
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
