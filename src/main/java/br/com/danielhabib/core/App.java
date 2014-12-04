package br.com.danielhabib.core;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JApplet;
import javax.swing.JFrame;

public class App {
	private static final int OFFSET = 32;
	private static int speed = 64;
	private static final int WINDOW_SIZE = OFFSET + 64 * 6;

	public static void main(String[] args) throws InterruptedException {
		JFrame f = new JFrame("Psico");
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		final Psico psico = new Psico(new CounterClockWiseDirection(), new RegularMoveHandler(new Position(64, 64 * 4), speed));
		Environment env = new Environment("wwwwww\nw    w\nw    w\nw    w\nw    w\nwwwwww");
		JApplet applet = new Main2D(psico, env);
		f.getContentPane().add("Center", applet);
		applet.init();
		f.pack();
		f.setSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
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
				case 's':
					speed *= 2;
					psico.setSpeed(speed);
					break;
				case 'x':
					speed /= 2;
					if (speed == 0) {
						speed = 1;
					}
					psico.setSpeed(speed);
					break;
				case 'q':
					System.exit(0);
					break;
				}
				System.out.println("DEBUG: speed = " + speed + ". Position = " + psico.getPosition().toString());
			}
		});
	}
}
