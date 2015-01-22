package br.com.danielhabib.unit.core;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import br.com.danielhabib.core.Config;
import br.com.danielhabib.core.component.Ball;
import br.com.danielhabib.core.component.Goal;
import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.PsicoComponent;
import br.com.danielhabib.core.rules.GoalRule;

public class GoalRuleTest {
	@Test
	public void levelOver_NoBall1Goal_LeveIsOver() throws Exception {
		PsicoComponent goal = new Goal(new Position(Config.SIZE, 0));
		GoalRule rule = new GoalRule(new ArrayList<PsicoComponent>(), goal);

		boolean isOver = rule.isLevelOver();

		assertEquals(true, isOver);
	}

	@Test
	public void levelOver_1Ball1Goal_NotOverIfBallIsNotInsideIt() throws Exception {
		PsicoComponent ball = new Ball(new Position(0, 0));
		PsicoComponent goal = new Goal(new Position(Config.SIZE, 0));
		GoalRule rule = new GoalRule(ball, goal);

		boolean isOver = rule.isLevelOver();

		assertEquals(false, isOver);
	}

	@Test
	public void levelOver_1Ball1Goal_IsOverIfBallIsInsideIt() throws Exception {
		PsicoComponent ball = new Ball(new Position(0, 0));
		PsicoComponent goal = new Goal(new Position(0, 0));
		GoalRule rule = new GoalRule(ball, goal);

		boolean isOver = rule.isLevelOver();

		assertEquals(true, isOver);
	}

	@Test
	public void levelOver_2Balls1Goal_IsOverIfAllBallsAreInsideIt() throws Exception {
		PsicoComponent ball1 = new Ball(new Position(0, 0));
		PsicoComponent ball2 = new Ball(new Position(0, 0));
		PsicoComponent goal = new Goal(new Position(0, 0));
		GoalRule rule = new GoalRule(Arrays.asList(ball1, ball2), goal);

		boolean isOver = rule.isLevelOver();

		assertEquals(true, isOver);
	}

	@Test
	public void levelOver_2Balls1Goal_IsNotOverIfAllBallsAreNotInsideIt() throws Exception {
		PsicoComponent ball1 = new Ball(new Position(0, Config.SIZE));
		PsicoComponent ball2 = new Ball(new Position(0, 0));
		PsicoComponent goal = new Goal(new Position(0, Config.SIZE));
		GoalRule rule = new GoalRule(Arrays.asList(ball1, ball2), goal);

		boolean isOver = rule.isLevelOver();

		assertEquals(false, isOver);
	}
}
