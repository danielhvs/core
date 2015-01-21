package br.com.danielhabib.integration.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.danielhabib.core.Config;
import br.com.danielhabib.core.Environment;
import br.com.danielhabib.core.GoalRule;
import br.com.danielhabib.core.Position;

public class DrawingTest extends AbstractIntegrationTest {

	@Override
	protected void testIt() throws InterruptedException {
	}

	@Override
	protected void setup() {
		Environment env = parser.getEnv();
		for (int i = 0; i < 12; i++) {
			Position position = psico.getPosition().add(new Position(Config.SIZE, Config.SIZE));
			env.createBall(position);
		}
		for (int i = 0; i < 101; i++) {
			Position position = psico.getPosition().add(new Position(0, Config.SIZE));
			env.createBall(position);
		}
	}

	@Override
	protected int timeoutMillis() {
		return 1500;
	}

	@Override
	protected List<GoalRule> rules() {
		return new ArrayList<GoalRule>();
	}

	@Override
	protected List<String> levels() {
		return Arrays.asList("w:2,2\nr:3,3-4,3\np:0,0");
	}
}
