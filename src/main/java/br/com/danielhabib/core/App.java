package br.com.danielhabib.core;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JApplet;
import javax.swing.JFrame;

public class App {
	private static int speed = 64;
	private static final int WINDOW_SIZE = 550;

	public static void main(String[] args) throws InterruptedException {
		JFrame f = new JFrame("Psico");
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		final Psico psico = new Psico(new CounterClockWiseDirection(), new RegularMoveHandler(new Position(0, WINDOW_SIZE / 2), speed));
		JApplet applet = new Main2D(psico);
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
			}
		});

	}
}
