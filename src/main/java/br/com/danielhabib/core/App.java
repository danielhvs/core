package br.com.danielhabib.core;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.JApplet;
import javax.swing.JFrame;

public class App {
	private static final int WINDOW_SIZE = 550;

	public static void main(String[] args) throws InterruptedException {
		JFrame f = new JFrame("Psico");
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		Psico psico = new Psico(new CounterClockWiseDirection(),
				new RegularMoveHandler(new Position(0, 0)));
		JApplet applet = new Main2D(psico);
		f.getContentPane().add("Center", applet);
		applet.init();
		f.pack();
		f.setSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
		f.setVisible(true);

		for (int i = 0; i < 100; i++) {
			psico.move();
			TimeUnit.MILLISECONDS.sleep(100);
		}
	}
}
