package br.com.danielhabib.core;

public class GoalRule {

	private final PsicoComponent ball;
	private final PsicoComponent goal;

	public GoalRule(PsicoComponent ball, PsicoComponent goal) {
		this.ball = ball;
		this.goal = goal;
	}

	public boolean isLevelOver() {
		return ball.getPosition().equals(goal.getPosition());
	}

}
