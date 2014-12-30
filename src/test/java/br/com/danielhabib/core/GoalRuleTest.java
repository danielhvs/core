package br.com.danielhabib.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GoalRuleTest {
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
}
