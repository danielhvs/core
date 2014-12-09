package br.com.danielhabib.core;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.googlecode.zohhak.api.runners.ZohhakRunner;

@RunWith(ZohhakRunner.class)
public class RegularMoveHandlerTest {
	@Test
	public void move_ThereIsAWall_DoesntMove() throws Exception {
		Position initialPosition = new Position(0, 0);
		RegularMoveHandler moveHandler = new RegularMoveHandler(initialPosition, new MovingRules(new Environment(" w")));

		moveHandler.move(Direction.RIGHT);

		assertThat(moveHandler.getPosition(), is(equalTo(initialPosition)));
	}

	@Test
	public void hasBall_ThereIsntABall_False() throws Exception {
		RegularMoveHandler moveHandler = new RegularMoveHandler(new Position(0, 0), new MovingRules(new Environment("")));

		assertThat(moveHandler.hasBall(), is(equalTo(false)));
	}

	@Test
	public void hasBall_ThereIsABall_True() throws Exception {
		RegularMoveHandler moveHandler = new RegularMoveHandler(new Position(0, 0), new MovingRules(new Environment("o")));

		assertThat(moveHandler.hasBall(), is(equalTo(true)));
	}
}
