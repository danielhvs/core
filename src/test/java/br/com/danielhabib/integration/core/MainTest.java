package br.com.danielhabib.integration.core;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.Ignore;

import br.com.danielhabib.core.App;
import br.com.danielhabib.core.Config;
import br.com.danielhabib.core.CounterClockWiseDirection;
import br.com.danielhabib.core.Environment;
import br.com.danielhabib.core.ImageHandler;
import br.com.danielhabib.core.Main2D;
import br.com.danielhabib.core.Position;
import br.com.danielhabib.core.Psico;
import br.com.danielhabib.core.RegularMoveHandler;

@Ignore
public class MainTest extends App {
	private static final int TIMEOUT = 100;

	public static void main(String[] args) throws IOException, InterruptedException {
		frame = buildFrame();

		Environment env = new Environment(
				"wwwwww\n" +
						"w g gw\n" +
						"wggoow\n" +
						"woooow\n" +
						"woooow\n" +
				"wwwwww");

		psico = new Psico(new CounterClockWiseDirection(), new RegularMoveHandler(new Position(Config.SIZE, Config.SIZE * 4), env), new ImageHandler());
		applet = new Main2D(psico, env);

		setupFrame();
		setupCommands();

		for (int i = 0; i < 11; i++) {
			Position position = psico.getPosition().add(new Position(Config.SIZE, -2 * Config.SIZE));
			env.createBall(position);
		}
		for (int i = 0; i < 100; i++) {
			Position position = psico.getPosition().add(new Position(0, -2 * Config.SIZE));
			env.createBall(position);
		}

		grab();
		move();
		drop();
		move();
		grab();
		left();
		drop();
		move();
		move();
		grab();
		left();
		left();
		drop();
		up();
		grab();
		down();
		drop();
		up();
		left();
		grab();
		move();
		down();
		drop();
		move();
	}

	private static void down() throws InterruptedException {
		turn();
		turn();
		turn();
		move();
		turn();
	}

	private static void up() throws InterruptedException {
		turn();
		move();
		turn();
		turn();
		turn();
	}

	private static void left() throws InterruptedException {
		turn();
		turn();
		move();
		turn();
		turn();
	}

	private static void drop() throws InterruptedException {
		sleep();
		psico.drop();
	}

	private static void grab() throws InterruptedException {
		sleep();
		psico.grab();
	}

	private static void move() throws InterruptedException {
		sleep();
		psico.move();
	}

	private static void turn() throws InterruptedException {
		sleep();
		psico.turn();
	}

	private static void sleep() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(TIMEOUT);
	}
}
