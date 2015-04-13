package br.com.danielhabib.unit.core;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.danielhabib.core.Config;
import br.com.danielhabib.core.component.Ball;
import br.com.danielhabib.core.component.Environment;
import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.Psico;
import br.com.danielhabib.core.component.PsicoComponent;
import br.com.danielhabib.core.nulls.NullComponent;
import br.com.danielhabib.core.rules.Direction;
import br.com.danielhabib.core.rules.DirectionHandler;
import br.com.danielhabib.core.rules.IMoveHandler;
import br.com.danielhabib.core.rules.IPsicoObserver;
import br.com.danielhabib.core.rules.ImageHandler;
import br.com.danielhabib.core.rules.RegularMoveHandler;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;

@RunWith(ZohhakRunner.class)
public class PsicoTest {
	private Psico psico;
	private DirectionHandler directionHandler;

	@Mock
	private IPsicoObserver observer;

	@Mock
	private ImageHandler imageHandler;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		directionHandler = new DirectionHandler();
		Map<Integer, Integer> directionMap = new HashMap<Integer, Integer>();
		directionMap.put(Direction.UP, Direction.LEFT);
		directionMap.put(Direction.DOWN, Direction.RIGHT);
		directionMap.put(Direction.LEFT, Direction.DOWN);
		directionMap.put(Direction.RIGHT, Direction.UP);
		directionHandler.setDirectionsMap(directionMap);
		directionHandler.setDirection(Direction.RIGHT);
		psico = newPsicoWithEnv("");
	}

	@Test
	public void initialXDefault() throws Exception {
		assertThat(x(), is(equalTo(0)));
	}

	@Test
	public void initialYDefault() throws Exception {
		assertThat(y(), is(equalTo(0)));
	}

	@TestWith({ "0,0", "1,90", "2,180", "3,270", "4,0", "5,90" })
	public void turn_ChangesDirection(int qtdGiros, Integer esperado) throws Exception {
		for (int i = 0; i < qtdGiros; i++) {
			psico.turn();
		}
		assertThat(psico.getDirection(), is(equalTo(esperado)));
	}

	@Test
	public void move_Right() throws Exception {
		psico.move();
		assertThat(x(), is(equalTo(Config.SIZE)));
	}

	@Test
	public void move_Left() throws Exception {
		psico.turn();
		psico.turn();
		psico.move();
		assertThat(x(), is(equalTo(-Config.SIZE)));
	}

	@Test
	public void move_Up() throws Exception {
		psico.turn();
		psico.move();
		assertThat(y(), is(equalTo(-Config.SIZE)));
	}

	@Test
	public void move_Down() throws Exception {
		psico.turn();
		psico.turn();
		psico.turn();
		psico.move();
		assertThat(y(), is(equalTo(Config.SIZE)));
	}

	@Test
	public void move_whenPositionChanges_oberverGetsNotification() throws Exception {
		psico.setObserver(observer);

		psico.move();

		verify(observer).hasChanged();
	}

	@Test
	public void turn_whenDirectionChanges_oberverGetsNotification() throws Exception {
		psico.setObserver(observer);

		psico.turn();

		verify(observer).hasChanged();
	}

	@Test
	public void whenNoObserverIsSetted_DefaultObserverUsed_DoesntThrowsNullPointer() throws Exception {
		psico.move();
		psico.turn();
	}

	@Test
	public void grab_ThereIsABall_NowHasIt() throws Exception {
		psico = newPsicoWithEnv("o");
		psico.setObserver(observer);
		PsicoComponent expected = new Ball(new Position(0, 0), Config.SIZE);

		psico.grab();

		assertThat(psico.getBall(), is(equalTo(expected)));
		verify(observer).hasChanged();
	}

	@Test
	public void grab_MoreBalls_NowHasOne() throws Exception {
		psico = newPsicoWithEnv("ooo");
		psico.setObserver(observer);
		PsicoComponent expected = new Ball(new Position(1, 0), Config.SIZE);

		psico.move();
		psico.grab();

		assertThat(psico.getBall(), is(equalTo(expected)));
	}

	@Test
	public void grab_ThereIsNoBall_DoesntHaveIt() throws Exception {
		NullComponent nullComponent = new NullComponent();

		psico.grab();

		assertEquals(nullComponent, psico.getBall());
	}

	@Test
	public void grabThenMove_ThereIsABall_MovesWithPsico() throws Exception {
		psico = newPsicoWithEnv("o");

		psico.grab();
		psico.move();

		assertThat(psico.getBall().getPosition(), is(equalTo(psico.getPosition())));
	}

	@Test
	public void drop_HasABall_DoesntHaveItAnymore() throws Exception {
		psico = newPsicoWithEnv("o");
		psico.setObserver(observer);

		psico.grab();
		psico.drop();

		assertEquals(new NullComponent(), psico.getBall());
		verify(observer, times(2)).hasChanged();
	}

	@Test
	public void dropThenGrab_HasNoBall_CantDrop() throws Exception {
		psico.drop();
		psico.grab();
		assertEquals(new NullComponent(), psico.getBall());
	}

	@Test
	public void grabThenDrop_ItsTheSameBall() throws Exception {
		psico = newPsicoWithEnv("o");

		psico.grab();
		PsicoComponent ball = psico.getBall();

		psico.drop();
		psico.grab();
		PsicoComponent sameBall = psico.getBall();

		assertSame(ball, sameBall);
	}

	private int y() {
		return psico.getPosition().getY();
	}

	private int x() {
		return psico.getPosition().getX();
	}

	private Psico newPsicoWithEnv(String envString) {
		return new Psico(directionHandler, newMoveHandlerWithEnv(envString), imageHandler);
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
