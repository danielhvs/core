package br.com.danielhabib.unit.core;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import br.com.danielhabib.core.component.Ball;
import br.com.danielhabib.core.component.Goal;
import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.PsicoComponent;
import br.com.danielhabib.core.rules.GoalRule;

public class GoalRuleTest {
	private static final int CONFIG_SIZE = 64;

	@Test
	public void levelOver_NoBall1Goal_LeveIsOver() throws Exception {
		PsicoComponent goal = newGoalAt(new Position(1, 0));
		GoalRule rule = new GoalRule(new ArrayList<PsicoComponent>(), goal);

		boolean isOver = rule.isLevelOver();

		assertEquals(true, isOver);
	}

	@Test
	public void levelOver_1Ball1Goal_NotOverIfBallIsNotInsideIt() throws Exception {
		PsicoComponent ball = newBallAt(new Position(0, 0));
		PsicoComponent goal = newGoalAt(new Position(1, 0));
		GoalRule rule = new GoalRule(ball, goal);

		boolean isOver = rule.isLevelOver();

		assertEquals(false, isOver);
	}

	@Test
	public void levelOver_1Ball1Goal_IsOverIfBallIsInsideIt() throws Exception {
		PsicoComponent ball = newBallAt(new Position(0, 0));
		PsicoComponent goal = newGoalAt(new Position(0, 0));
		GoalRule rule = new GoalRule(ball, goal);

		boolean isOver = rule.isLevelOver();

		assertEquals(true, isOver);
	}

	@Test
	public void levelOver_2Balls1Goal_IsOverIfAllBallsAreInsideIt() throws Exception {
		PsicoComponent ball1 = newBallAt(new Position(0, 0));
		PsicoComponent ball2 = newBallAt(new Position(0, 0));
		PsicoComponent goal = newGoalAt(new Position(0, 0));
		GoalRule rule = new GoalRule(Arrays.asList(ball1, ball2), goal);

		boolean isOver = rule.isLevelOver();

		assertEquals(true, isOver);
	}

	@Test
	public void levelOver_2Balls1Goal_IsNotOverIfAllBallsAreNotInsideIt() throws Exception {
		PsicoComponent ball1 = newBallAt(new Position(0, 1));
		PsicoComponent ball2 = newBallAt(new Position(0, 0));
		PsicoComponent goal = newGoalAt(new Position(0, 1));
		GoalRule rule = new GoalRule(Arrays.asList(ball1, ball2), goal);

		boolean isOver = rule.isLevelOver();

		assertEquals(false, isOver);
	}

	private Ball newBallAt(Position position) {
		return new Ball(position, CONFIG_SIZE);
	}

	private Goal newGoalAt(Position position) {
		return new Goal(position, CONFIG_SIZE);
	}

}
