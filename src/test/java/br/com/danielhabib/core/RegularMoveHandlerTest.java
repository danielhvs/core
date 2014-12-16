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
		IMoveHandler moveHandler = newMoveHandlerWithEnv(" w");

		moveHandler.move(Direction.RIGHT);

		assertThat(moveHandler.getPosition(), is(equalTo(new Position(0, 0))));
	}

	@Test
	public void canMove_NoWall_CanMove() throws Exception {
		Position oneSizeToTheRight = new Position(Config.SIZE, 0);
		IMoveHandler moveHandler = newMoveHandlerWithEnv("");

		moveHandler.move(Direction.RIGHT);

		assertThat(moveHandler.getPosition(), is(equalTo(oneSizeToTheRight)));
	}

	private IMoveHandler newMoveHandlerWithEnv(String string) {
		return new RegularMoveHandler(new Position(0, 0), new Environment(string));
	}

}
