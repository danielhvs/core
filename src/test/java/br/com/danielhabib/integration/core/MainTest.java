package br.com.danielhabib.integration.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.danielhabib.core.Config;
import br.com.danielhabib.core.Environment;
import br.com.danielhabib.core.GoalRule;
import br.com.danielhabib.core.Position;

public class MainTest extends AbstractIntegrationTest {

	@Override
	protected Position psicoInitialPosition() {
		return new Position(Config.SIZE, Config.SIZE * 4);
	}

	@Override
	protected Environment setupEnv() throws IOException {
		return new Environment(
				"wwwwww\n" +
						"w g gw\n" +
						"wggoow\n" +
						"woooow\n" +
						"woooow\n" +
				"wwwwww");
	}

	@Override
	protected void testIt() throws InterruptedException {
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

	@Override
	protected int setTimeoutMillis() {
		return 25;
	}

	@Override
	protected List<GoalRule> rules() {
		return new ArrayList<GoalRule>();
	}
}
