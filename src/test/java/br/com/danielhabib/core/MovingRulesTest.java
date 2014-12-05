package br.com.danielhabib.core;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class MovingRulesTest {
	@Test
	public void canMove_NoWall_CanMove() throws Exception {
		MovingRules rules = new MovingRules(new Environment(""));

		boolean canMove = rules.canMove(new Position(0, 0));

		assertThat(canMove, is(equalTo(true)));
	}

	@Test
	public void canMove_ToWall_CantMove() throws Exception {
		MovingRules rules = new MovingRules(new Environment("w"));

		boolean canMove = rules.canMove(new Position(0, 0));

		assertThat(canMove, is(equalTo(false)));
	}
}
