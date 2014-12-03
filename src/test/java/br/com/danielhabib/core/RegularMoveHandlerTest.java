package br.com.danielhabib.core;

import static org.junit.Assert.assertEquals;

import org.junit.runner.RunWith;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;

@RunWith(ZohhakRunner.class)
public class RegularMoveHandlerTest {
	@TestWith({ "10,RIGHT,10,0", "5,LEFT,-5,0", "-3,UP,0,3", "8,DOWN,0,8" })
	public void setSpeed_whenMovingFromOrigin_updatesPositionWithSpeed(int speed, Direction direction, int expectX, int expectY) throws Exception {
		RegularMoveHandler moveHandler = new RegularMoveHandler(new Position(0, 0), speed);

		moveHandler.move(direction);

		Position expected = new Position(expectX, expectY);
		assertEquals(expected, moveHandler.getPosition());
	}
}
