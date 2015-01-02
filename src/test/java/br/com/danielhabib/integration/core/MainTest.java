package br.com.danielhabib.integration.core;

import java.util.ArrayList;
import java.util.List;

import br.com.danielhabib.core.Config;
import br.com.danielhabib.core.GoalRule;
import br.com.danielhabib.core.Position;

public class MainTest extends AbstractIntegrationTest {

	@Override
	protected Position psicoInitialPosition() {
		return new Position(Config.SIZE, Config.SIZE * 4);
	}

	@Override
	protected void testIt() throws InterruptedException {
	}

	@Override
	protected void setup() {
		for (int i = 0; i < 12; i++) {
			Position position = psico.getPosition().add(new Position(Config.SIZE, -2 * Config.SIZE));
			env.createBall(position);
		}
		for (int i = 0; i < 101; i++) {
			Position position = psico.getPosition().add(new Position(0, -2 * Config.SIZE));
			env.createBall(position);
		}
	}

	@Override
	protected int setTimeoutMillis() {
		return 1000;
	}

	@Override
	protected List<GoalRule> rules() {
		return new ArrayList<GoalRule>();
	}

	@Override
	protected String level() {
		return "";
	}
}
