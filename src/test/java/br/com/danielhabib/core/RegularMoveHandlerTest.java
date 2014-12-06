package br.com.danielhabib.core;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;

@RunWith(ZohhakRunner.class)
public class RegularMoveHandlerTest {
	@TestWith({ "10,RIGHT,10,0", "5,LEFT,-5,0", "-3,UP,0,3", "8,DOWN,0,8" })
	public void setSpeed_whenMovingFromOrigin_updatesPositionWithSpeed(int speed, Direction direction, int expectX, int expectY) throws Exception {
		RegularMoveHandler moveHandler = new RegularMoveHandler(new Position(0, 0), speed, new MovingRules(new Environment("")));

		moveHandler.move(direction);

		Position expected = new Position(expectX, expectY);
		assertThat(moveHandler.getPosition(), is(equalTo(expected)));
	}

	@Test
	public void move_ThereIsAWall_DoesntMove() throws Exception {
		Position initialPosition = new Position(0, 0);
		RegularMoveHandler moveHandler = new RegularMoveHandler(initialPosition, Config.SIZE, new MovingRules(new Environment(" w")));

		moveHandler.move(Direction.RIGHT);

		assertThat(moveHandler.getPosition(), is(equalTo(initialPosition)));
	}
}
