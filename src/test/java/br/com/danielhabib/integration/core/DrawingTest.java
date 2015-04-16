package br.com.danielhabib.integration.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.danielhabib.core.component.Environment;
import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.rules.GoalRule;

public class DrawingTest extends AbstractIntegrationTest {

	@Override
	protected void testIt() throws InterruptedException {
	}

	@Override
	protected void setup() {
		Environment env = parser.getEnv();
		Position origin = new Position(1, 0);
		for (int i = 0; i < 12; i++) {
			Position position = origin.add(new Position(1, 1));
			env.createBall(position);
		}
		for (int i = 0; i < 101; i++) {
			Position position = origin.add(new Position(0, 1));
			env.createBall(position);
		}
	}

	@Override
	protected int timeoutMillis() {
		return 2000;
	}

	@Override
	protected List<GoalRule> rules() {
		return new ArrayList<GoalRule>();
	}

	@Override
	protected List<String> levels() {
		return Arrays.asList("w:0,0-4,0\nw:5,0-5,4\nw:0,5-5,5\nw:0,1-0,4\nr:2,3-3,3\np:2,2");
	}
}
