package br.com.danielhabib.integration.core;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.junit.Test;

import br.com.danielhabib.core.Config;
import br.com.danielhabib.core.CounterClockWiseDirection;
import br.com.danielhabib.core.Environment;
import br.com.danielhabib.core.ImageHandler;
import br.com.danielhabib.core.Main2D;
import br.com.danielhabib.core.Position;
import br.com.danielhabib.core.Psico;
import br.com.danielhabib.core.RegularMoveHandler;

public abstract class AbstractIntegrationTest {
	protected static final int WINDOW_SIZE = Config.SIZE / 2 + Config.SIZE * 6;
	protected static JFrame frame;
	protected static JApplet applet;
	protected static Psico psico;
	protected static Environment env;

	@Test
	public void integrationTest() throws Exception {
		frame = buildFrame();

		env = setupEnv();
		psico = setupPsico(psicoInitialPosition());
		applet = new Main2D(psico, env);
		timeout = setTimeoutMillis();

		setupFrame();
		setupCommands();

		sleep();
		testIt();
	}

	protected abstract void testIt() throws Exception;

	protected abstract Position psicoInitialPosition();

	private Psico setupPsico(Position position) {
		return new Psico(new CounterClockWiseDirection(), new RegularMoveHandler(position, env), new ImageHandler());
	}

	protected abstract Environment setupEnv() throws IOException;

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
		sleep();
		psico.drop();
	}

	protected void grab() throws InterruptedException {
		sleep();
		psico.grab();
	}

	protected void move() throws InterruptedException {
		sleep();
		psico.move();
	}

	protected void turn() throws InterruptedException {
		sleep();
		psico.turn();
	}

	protected void sleep() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(timeout);
	}
}
