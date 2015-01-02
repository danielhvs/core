package br.com.danielhabib.core;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import br.com.danielhabib.core.builder.LevelParser;

public class LevelParserTest {
	@Test
	public void parse_W() throws Exception {
		LevelParser parser = new LevelParser("w:0,0");

		List<PsicoComponent> walls = parser.getWalls();

		assertEquals(wall(0, 0), walls.get(0));
	}

	@Test
	public void parse_O() throws Exception {
		LevelParser parser = new LevelParser("o:0,0");

		List<PsicoComponent> balls = parser.getBalls();

		assertEquals(ball(0, 0), balls.get(0));
	}

	@Test
	public void parse_2Os() throws Exception {
		LevelParser parser = new LevelParser("o:0,0\no:1,0");

		List<PsicoComponent> balls = parser.getBalls();

		assertEquals(ball(Config.SIZE, 0), balls.get(1));
	}

	@Test
	public void parse_2OsSamePosition() throws Exception {
		LevelParser parser = new LevelParser("o:0,0\no:0,0");

		List<PsicoComponent> balls = parser.getBalls();

		assertEquals(ball(0, 0), balls.get(0));
		assertEquals(ball(0, 0), balls.get(1));
	}

	@Test
	public void parse_GIsImplicitInRule() throws Exception {
		LevelParser parser = new LevelParser("r:0,0-0,0");

		List<GoalRule> rules = parser.getGoalRules();
		List<PsicoComponent> goals = parser.getGoals();
		List<PsicoComponent> balls = parser.getBalls();

		assertEquals(new Goal(new Position(0, 0)), goals.get(0));
		assertEquals(ball(0, 0), balls.get(0));
	}

	@Test
	public void parse_ManyGoals() throws Exception {
		LevelParser parser = new LevelParser("r:0,0-0,0\nr:1,0-0,0\nr:0,1-1,0");

		List<PsicoComponent> goals = parser.getGoals();
		List<PsicoComponent> balls = parser.getBalls();

		assertEquals(ball(0, 0), balls.get(0));
		assertEquals(goal(0, 0), goals.get(0));

		assertEquals(ball(Config.SIZE, 0), balls.get(1));

		assertEquals(ball(0, Config.SIZE), balls.get(2));
		assertEquals(goal(Config.SIZE, 0), goals.get(1));
	}

	@Test
	public void parse_ComplexCenario() throws Exception {
		LevelParser parser = new LevelParser("w:1,0\no:2,0\nr:0,1-2,0");

		List<PsicoComponent> goals = parser.getGoals();
		List<PsicoComponent> balls = parser.getBalls();
		List<PsicoComponent> walls = parser.getWalls();

		assertEquals(wall(Config.SIZE, 0), walls.get(0));

		assertEquals(ball(2 * Config.SIZE, 0), balls.get(0));

		assertEquals(ball(0, Config.SIZE), balls.get(1));
		assertEquals(goal(2 * Config.SIZE, 0), goals.get(0));
	}

	private Ball ball(int x, int y) {
		return new Ball(new Position(x, y));
	}

	private Wall wall(int x, int y) {
		return new Wall(new Position(x, y));
	}

	private Goal goal(int x, int y) {
		return new Goal(new Position(x, y));
	}

}
