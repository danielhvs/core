package br.com.danielhabib.core;

import java.util.List;

public interface IMoveHandler {

	boolean move(Direction direction);

	Position getPosition();

	PsicoComponent getBall();

	PsicoComponent dropBall(PsicoComponent ball);

	void setRules(List<GoalRule> rules);

	void setObserver(IRulesObserver rulesObserver);

}
