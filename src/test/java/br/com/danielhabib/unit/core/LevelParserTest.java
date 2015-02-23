package br.com.danielhabib.unit.core;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import br.com.danielhabib.core.Config;
import br.com.danielhabib.core.builder.LevelParser;
import br.com.danielhabib.core.component.Ball;
import br.com.danielhabib.core.component.Goal;
import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.Psico;
import br.com.danielhabib.core.component.PsicoComponent;
import br.com.danielhabib.core.component.Wall;
import br.com.danielhabib.core.rules.GoalRule;

public class LevelParserTest {
	@Test
	public void parse_Empty_Empty() throws Exception {
		LevelParser parser = new LevelParser("");

		assertEquals(0, parser.getBalls().size());
		assertEquals(0, parser.getWalls().size());
		assertEquals(0, parser.getGoals().size());
		assertEquals(0, parser.getGoalRules().size());
	}

	@Test
	public void parse_Psico() throws Exception {
		LevelParser parser = new LevelParser("p:1,0");

		Psico psico = parser.getPsico();

		assertEquals(new Position(Config.SIZE, 0), psico.getPosition());
	}

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

		List<PsicoComponent> goals = parser.getGoals();
		List<PsicoComponent> balls = parser.getBalls();

		assertEquals(new Goal(new Position(0, 0)), goals.get(0));
		assertEquals(ball(0, 0), balls.get(0));
	}

	@Test
	public void parse_ManyGoals_OnlyOneGoalPerPosition() throws Exception {
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
	public void parse_LineYWall_ManyWallsInYLine() throws Exception {
		LevelParser parser = new LevelParser("w:0,1-0,5");
		List<PsicoComponent> walls = parser.getWalls();
		assertEquals(5, walls.size());
	}

	@Test
	public void parse_LineXWall_ManyWallsInLine() throws Exception {
		LevelParser parser = new LevelParser("w:1,0-5,0");
		List<PsicoComponent> walls = parser.getWalls();
		assertEquals(5, walls.size());
	}

	@Test
	public void parse_LineXBall_ManyBallsInLine() throws Exception {
		LevelParser parser = new LevelParser("o:1,0-5,0");
		List<PsicoComponent> balls = parser.getBalls();
		assertEquals(5, balls.size());
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

	@Test
	public void getGoalRule_LevelNotOver() {
		LevelParser parser = new LevelParser("r:0,1-2,0");
		List<GoalRule> rules = parser.getGoalRules();
		GoalRule rule = rules.get(0);

		assertEquals(false, rule.isLevelOver());
	}

	@Test
	public void getGoalRule_LevelIsOver() {
		LevelParser parser = new LevelParser("r:0,0-0,0");
		List<GoalRule> rules = parser.getGoalRules();
		GoalRule rule = rules.get(0);

		assertEquals(true, rule.isLevelOver());
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
