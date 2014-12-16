package br.com.danielhabib.integration.core;

import java.io.File;
import java.io.IOException;

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
	public static void main(String[] args) throws IOException {
		frame = buildFrame();

		Environment env = new Environment(new File("level_1.txt"));
		psico = new Psico(new CounterClockWiseDirection(), new RegularMoveHandler(new Position(Config.SIZE, Config.SIZE * 4), env), new ImageHandler());
		applet = new Main2D(psico, env);

		setupFrame();
		setupCommands();

		psico.move();
		psico.move();
		psico.grab();
		psico.move();
		psico.turn();
		psico.move();
		psico.drop();
		psico.move();
	}
}
