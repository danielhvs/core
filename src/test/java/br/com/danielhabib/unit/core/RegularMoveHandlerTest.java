package br.com.danielhabib.unit.core;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.danielhabib.core.component.Ball;
import br.com.danielhabib.core.component.Environment;
import br.com.danielhabib.core.component.Goal;
import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.rules.Direction;
import br.com.danielhabib.core.rules.GoalRule;
import br.com.danielhabib.core.rules.IMoveHandler;
import br.com.danielhabib.core.rules.IRulesObserver;
import br.com.danielhabib.core.rules.RegularMoveHandler;

import com.googlecode.zohhak.api.runners.ZohhakRunner;

@RunWith(ZohhakRunner.class)
public class RegularMoveHandlerTest {
	private static final int CONFIG_SIZE = 64;

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
		Position oneSizeToTheRight = new Position(1, 0);
		IMoveHandler moveHandler = newMoveHandlerWithEnv("");

		moveHandler.move(Direction.RIGHT);

		assertThat(moveHandler.getPosition(), is(equalTo(oneSizeToTheRight)));
	}

	@Test
	public void dropBall_LevelOver_Notifies() throws Exception {
		IMoveHandler moveHandler = newMoveHandlerWithEnv("g");
		moveHandler.setObserver(observer);

		Ball ball = new Ball(new Position(0, 0), CONFIG_SIZE);
		moveHandler.setRules(Arrays.asList(new GoalRule(ball, new Goal(new Position(0, 0), CONFIG_SIZE))));

		moveHandler.dropBall(ball);

		verify(observer).levelIsOver();
	}

	private IMoveHandler newMoveHandlerWithEnv(String string) {
		RegularMoveHandler regularMoveHandler = new RegularMoveHandler(new Position(0, 0));
		regularMoveHandler.setEnv(new Environment(string));
		Map<Integer, Position> speedMap = new HashMap<Integer, Position>();
		speedMap.put(Direction.UP, new Position(0, -1));
		speedMap.put(Direction.DOWN, new Position(0, 1));
		speedMap.put(Direction.LEFT, new Position(-1, 0));
		speedMap.put(Direction.RIGHT, new Position(1, 0));
		regularMoveHandler.setSpeedMap(speedMap);
		return regularMoveHandler;
	}

}
