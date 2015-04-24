package br.com.danielhabib.unit.core;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
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
import br.com.danielhabib.core.component.Wall;
import br.com.danielhabib.core.rules.GoalRule;
import br.com.danielhabib.core.rules.GrabbingRules;
import br.com.danielhabib.core.rules.MovingRules;

public class LevelParserTest {
	@Rule
	public TemporaryFolder tmpDir = new TemporaryFolder();

	@Mock
	private ComponentBuilder componentBuilder;
	@Mock
	private GrabbingRules grabbingRules;
	@Mock
	private MovingRules movingRules;
	@Mock
	private ApplicationContext context;

	private static final int CONFIG_SIZE = 64;

	@Before
	public void testname() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockBuildsForPositionsUntil(5);
	}

	private void mockBuildsForPositionsUntil(int x) {
		for (int i = 0; i <= x; i++) {
			for (int j = 0; j <= x; j++) {
				Position position = new Position(i, j);
				when(componentBuilder.build('w', position)).thenReturn(new Wall(position, CONFIG_SIZE));
				when(componentBuilder.build('o', position)).thenReturn(new Ball(position, CONFIG_SIZE));
				when(componentBuilder.build('g', position)).thenReturn(new Goal(position, CONFIG_SIZE));
			}
		}
	}

	@Test
	public void parse_Empty_Empty() throws Exception {
		LevelParser parser = newLevelParser("");

		assertEquals(0, parser.getBalls().size());
		assertEquals(0, parser.getWalls().size());
		assertEquals(0, parser.getGoals().size());
		assertEquals(0, parser.getGoalRules().size());
	}

	@Test
	public void parse_Psico() throws Exception {
		LevelParser parser = newLevelParser("p:1,0");

		Psico psico = parser.getPsico();

		assertEquals(new Position(1, 0), psico.getPosition());
	}

	@Test
	public void parse_W() throws Exception {
		LevelParser parser = newLevelParser("w:0,0");

		List<Component> walls = parser.getWalls();

		assertEquals(wall(0, 0), walls.get(0));
	}

	@Test
	public void parse_O() throws Exception {


		LevelParser parser = newLevelParser("o:0,0");

		List<Component> balls = parser.getBalls();

		assertEquals(ball(0, 0), balls.get(0));
	}

	@Test
	public void parse_2Os() throws Exception {
		LevelParser parser = newLevelParser("o:0,0\no:1,0");

		List<Component> balls = parser.getBalls();

		assertEquals(ball(1, 0), balls.get(1));
	}

	@Test
	public void parse_2OsSamePosition() throws Exception {
		LevelParser parser = newLevelParser("o:0,0\no:0,0");

		List<Component> balls = parser.getBalls();

		assertEquals(ball(0, 0), balls.get(0));
		assertEquals(ball(0, 0), balls.get(1));
	}

	@Test
	public void parse_GIsImplicitInRule() throws Exception {
		LevelParser parser = newLevelParser("r:0,0-0,0");

		List<Component> goals = parser.getGoals();
		List<Component> balls = parser.getBalls();

		assertEquals(new Goal(new Position(0, 0), CONFIG_SIZE), goals.get(0));
		assertEquals(ball(0, 0), balls.get(0));
	}

	@Test
	public void parse_ManyGoals_OnlyOneGoalPerPosition() throws Exception {
		LevelParser parser = newLevelParser("r:0,0-0,0\nr:1,0-0,0\nr:0,1-1,0");

		List<Component> goals = parser.getGoals();
		List<Component> balls = parser.getBalls();

		assertEquals(ball(0, 0), balls.get(0));
		assertEquals(goal(0, 0), goals.get(0));

		assertEquals(ball(1, 0), balls.get(1));

		assertEquals(ball(0, 1), balls.get(2));
		assertEquals(goal(1, 0), goals.get(1));
	}

	@Test
	public void parse_LineYWall_ManyWallsInYLine() throws Exception {
		LevelParser parser = newLevelParser("w:0,1-0,5");
		List<Component> walls = parser.getWalls();
		assertEquals(5, walls.size());
	}

	@Test
	public void parse_LineXWall_ManyWallsInLine() throws Exception {
		LevelParser parser = newLevelParser("w:1,0-5,0");
		List<Component> walls = parser.getWalls();
		assertEquals(5, walls.size());
	}

	@Test
	public void parse_LineXBall_ManyBallsInLine() throws Exception {
		LevelParser parser = newLevelParser("o:1,0-5,0");
		List<Component> balls = parser.getBalls();
		assertEquals(5, balls.size());
	}

	@Test
	public void parse_ComplexCenario() throws Exception {
		LevelParser parser = newLevelParser("w:1,0\no:2,0\nr:0,1-2,0");

		List<Component> goals = parser.getGoals();
		List<Component> balls = parser.getBalls();
		List<Component> walls = parser.getWalls();

		assertEquals(wall(1, 0), walls.get(0));

		assertEquals(ball(2, 0), balls.get(0));

		assertEquals(ball(0, 1), balls.get(1));
		assertEquals(goal(2, 0), goals.get(0));
	}

	@Test
	public void getGoalRule_LevelNotOver() throws Exception {
		LevelParser parser = newLevelParser("r:0,1-2,0");
		List<GoalRule> rules = parser.getGoalRules();
		GoalRule rule = rules.get(0);

		assertEquals(false, rule.isLevelOver());
	}

	@Test
	public void getGoalRule_LevelIsOver() throws Exception {
		LevelParser parser = newLevelParser("r:0,0-0,0");
		List<GoalRule> rules = parser.getGoalRules();
		GoalRule rule = rules.get(0);

		assertEquals(true, rule.isLevelOver());
	}

	private LevelParser newLevelParser(String string) throws IOException {
		File file = tmpDir.newFile();
		FileUtils.writeStringToFile(file, string);
		LevelParser levelParser = new LevelParser();
		levelParser.setFile(file);
		levelParser.setMovingRules(movingRules);
		levelParser.setGrabbingRules(grabbingRules);
		levelParser.setComponentBuilder(componentBuilder);
		levelParser.setApplicationContext(context);

		levelParser.build();
		return levelParser;
	}

	private Ball ball(int x, int y) {
		return new Ball(new Position(x, y), CONFIG_SIZE);
	}

	private Wall wall(int x, int y) {
		return new Wall(new Position(x, y), CONFIG_SIZE);
	}

	private Goal goal(int x, int y) {
		return new Goal(new Position(x, y), CONFIG_SIZE);
	}

}
