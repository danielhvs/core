package br.com.danielhabib.core;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.googlecode.zohhak.api.runners.ZohhakRunner;

@RunWith(ZohhakRunner.class)
public class RegularMoveHandlerTest {
	@Mock
	private IRulesObserver observer;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

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

	@Test
	public void dropBall_LevelOver_Notifies() throws Exception {
		IMoveHandler moveHandler = newMoveHandlerWithEnv("g");
		moveHandler.setObserver(observer);

		Ball ball = new Ball(new Position(0, 0));
		moveHandler.setRules(Arrays.asList(new GoalRule(ball, new Goal(new Position(0, 0)))));

		moveHandler.dropBall(ball);

		verify(observer).levelIsOver();
	}

	private IMoveHandler newMoveHandlerWithEnv(String string) {
		return new RegularMoveHandler(new Position(0, 0), new Environment(string));
	}

}
