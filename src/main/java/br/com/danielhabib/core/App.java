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

public class App {
	private static final int WINDOW_SIZE = Config.SIZE / 2 + Config.SIZE * 6;

	public static void main(String[] args) throws InterruptedException, IOException {
		JFrame f = new JFrame("Psico");
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		Environment env = new Environment(new File("level_1.txt"));
		final Psico psico = new Psico(new CounterClockWiseDirection(), new RegularMoveHandler(new Position(Config.SIZE, Config.SIZE * 4), env));
		JApplet applet = new Main2D(psico, env);
		f.getContentPane().add("Center", applet);
		applet.init();
		f.pack();
		f.setSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE + Config.SIZE / 2));
		f.setVisible(true);

		f.addKeyListener(new KeyListener() {

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
}
