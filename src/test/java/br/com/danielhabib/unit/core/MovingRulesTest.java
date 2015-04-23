package br.com.danielhabib.unit.core;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import br.com.danielhabib.core.builder.LevelParser;
import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.rules.AMovingRules;
import br.com.danielhabib.core.rules.MovingRules;

public class MovingRulesTest {

	@Test
	public void move_ThereIsAWall_DoesntMove() throws Exception {
		AMovingRules rules = newMoveHandlerWithEnv("w:1,0");

		Position position = rules.move(new Position(0, 0), new Position(1, 0));

		assertThat(position, is(equalTo(new Position(0, 0))));
	}

	@Test
	public void canMove_NoWall_CanMove() throws Exception {
		Position oneSizeToTheRight = new Position(1, 0);
		AMovingRules rules = newMoveHandlerWithEnv("");

		Position position = rules.move(new Position(0, 0), new Position(1, 0));

		assertThat(position, is(equalTo(oneSizeToTheRight)));
	}

	private AMovingRules newMoveHandlerWithEnv(String string) {
		AMovingRules rules = new MovingRules();
		LevelParser levelParser = new LevelParser(string);
		rules.setLevelParser(levelParser);
		return rules;
	}
}
