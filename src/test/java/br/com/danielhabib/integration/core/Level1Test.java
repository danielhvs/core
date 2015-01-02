package br.com.danielhabib.integration.core;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import br.com.danielhabib.core.Config;
import br.com.danielhabib.core.Environment;
import br.com.danielhabib.core.GoalRule;
import br.com.danielhabib.core.IRulesObserver;
import br.com.danielhabib.core.Position;
import br.com.danielhabib.core.builder.LevelParser;

public class Level1Test extends AbstractIntegrationTest {

	private Observer rulesObserver;
	private LevelParser parser;

	@Override
	protected void testIt() throws Exception {
		move();
		grab();
		move();
		drop();

		assertEquals(true, rulesObserver.isOver);
	}

	@Override
	protected Position psicoInitialPosition() {
		return new Position(Config.SIZE, 0);
	}

	@Override
	protected Environment setupEnv() throws IOException {
		parser = new LevelParser("w:0,0\nr:2,0-3,0\nw:4,0");
		Environment env = new Environment();
		env.setBalls(parser.getBalls());
		env.setWalls(parser.getWalls());
		env.setGoals(parser.getGoals());
		return env;
	}

	@Override
	protected int setTimeoutMillis() {
		return 500;
	}

	@Override
	protected List<GoalRule> rules() {
		return parser.getGoalRules();
	}

	@Override
	protected void setup() {
		rulesObserver = new Observer();
		moveHandler.setObserver(rulesObserver);
	}

	class Observer implements IRulesObserver {

		private boolean isOver = false;

		public void levelIsOver() {
			isOver = true;
		}

	}

}
