package br.com.danielhabib.unit.core;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import br.com.danielhabib.core.builder.ComponentBuilder;
import br.com.danielhabib.core.builder.LevelParser;
import br.com.danielhabib.core.component.Ball;
import br.com.danielhabib.core.component.Component;
import br.com.danielhabib.core.component.Goal;
import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.Psico;
import br.com.danielhabib.core.nulls.NullComponent;
import br.com.danielhabib.core.rules.Direction;
import br.com.danielhabib.core.rules.DirectionHandler;
import br.com.danielhabib.core.rules.GrabbingRules;
import br.com.danielhabib.core.rules.IPsicoObserver;
import br.com.danielhabib.core.rules.MovingRules;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;

@RunWith(ZohhakRunner.class)
public class PsicoTest {
	private static final int CONFIG_SIZE = 64;

	@InjectMocks
	private Psico psico;

	private DirectionHandler directionHandler;

	@Rule
	public TemporaryFolder tmpDir = new TemporaryFolder();

	@Mock
	private IPsicoObserver observer;

	@Mock
	private ApplicationContext context;

	private Map<Integer, Position> speedMap;

	@Mock
	private ComponentBuilder componentBuilder;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

		speedMap = new HashMap<Integer, Position>();
		speedMap.put(0, new Position(1, 0));
		speedMap.put(90, new Position(0, -1));
		speedMap.put(180, new Position(-1, 0));
		speedMap.put(270, new Position(0, 1));

		directionHandler = new DirectionHandler();
		Map<Integer, Integer> directionMap = new HashMap<Integer, Integer>();
		directionMap.put(Direction.UP, Direction.LEFT);
		directionMap.put(Direction.DOWN, Direction.RIGHT);
		directionMap.put(Direction.LEFT, Direction.DOWN);
		directionMap.put(Direction.RIGHT, Direction.UP);
		directionHandler.setDirectionsMap(directionMap);
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
		assertThat(x(), is(equalTo(CONFIG_SIZE)));
	}

	@Test
	public void move_ThereIsAGoal_CanMove() throws Exception {
		when(componentBuilder.build('o', new Position(5, 5))).thenReturn(new Ball(new Position(5, 5), CONFIG_SIZE));
		when(componentBuilder.build('g', new Position(1, 0))).thenReturn(new Goal(new Position(1, 0), CONFIG_SIZE));
		psico = newPsicoWithEnv("r:5,5-1,0");

		psico.move();

		assertThat(x(), is(equalTo(CONFIG_SIZE)));
	}

	@Test
	public void move_Left() throws Exception {
		psico.turn();
		psico.turn();
		psico.move();
		assertThat(x(), is(equalTo(-CONFIG_SIZE)));
	}

	@Test
	public void move_Up() throws Exception {
		psico.turn();
		psico.move();
		assertThat(y(), is(equalTo(-CONFIG_SIZE)));
	}

	@Test
	public void move_Down() throws Exception {
		psico.turn();
		psico.turn();
		psico.turn();
		psico.move();
		assertThat(y(), is(equalTo(CONFIG_SIZE)));
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
		when(componentBuilder.build('o', new Position(0, 0))).thenReturn(new Ball(new Position(0, 0), CONFIG_SIZE));
		psico = newPsicoWithEnv("o:0,0");
		psico.setObserver(observer);
		Component expected = new Ball(new Position(0, 0), CONFIG_SIZE);

		psico.grab();

		assertThat(psico.getBall(), is(equalTo(expected)));
		verify(observer).hasChanged();
	}

	@Test
	public void grab_MoreBalls_NowHasOne() throws Exception {
		when(componentBuilder.build('o', new Position(0, 0))).thenReturn(new Ball(new Position(0, 0), CONFIG_SIZE));
		when(componentBuilder.build('o', new Position(1, 0))).thenReturn(new Ball(new Position(1, 0), CONFIG_SIZE));
		when(componentBuilder.build('o', new Position(2, 0))).thenReturn(new Ball(new Position(2, 0), CONFIG_SIZE));
		psico = newPsicoWithEnv("o:0,0-2,0");
		psico.setObserver(observer);
		Component expected = new Ball(new Position(1, 0), CONFIG_SIZE);

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
		when(componentBuilder.build('o', new Position(0, 0))).thenReturn(new Ball(new Position(0, 0), CONFIG_SIZE));
		psico = newPsicoWithEnv("o:0,0");

		psico.grab();
		psico.move();

		assertThat(psico.getBall().getPosition(), is(equalTo(psico.getPosition())));
	}

	@Test
	public void drop_HasABall_DoesntHaveItAnymore() throws Exception {
		when(componentBuilder.build('o', new Position(0, 0))).thenReturn(new Ball(new Position(0, 0), CONFIG_SIZE));
		psico = newPsicoWithEnv("o:0,0");
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
		when(componentBuilder.build('o', new Position(0, 0))).thenReturn(new Ball(new Position(0, 0), CONFIG_SIZE));
		psico = newPsicoWithEnv("o:0,0");

		psico.grab();
		Component ball = psico.getBall();

		psico.drop();
		psico.grab();
		Component sameBall = psico.getBall();

		assertSame(ball, sameBall);
	}

	private int y() {
		return psico.getPosition().getY();
	}

	private int x() {
		return psico.getPosition().getX();
	}

	private Psico newPsicoWithEnv(String envString) throws Exception {
		File file = tmpDir.newFile();
		envString += "\np:0,0";
		FileUtils.writeStringToFile(file, envString);
		LevelParser levelParser = new LevelParser();
		levelParser.setFile(file);

		GrabbingRules grabbingRules = new GrabbingRules();
		grabbingRules.setLevelParser(levelParser);
		levelParser.setGrabbingRules(grabbingRules);
		levelParser.setComponentBuilder(componentBuilder);
		levelParser.setDirectionRules(directionHandler);
		levelParser.setApplicationContext(context);
		levelParser.setMovingRules(new MovingRules());
		levelParser.build();

		Psico psico = levelParser.getPsico();
		psico.setSpeedMap(speedMap);
		return psico;
	}

}
