package br.com.danielhabib.core.rules;

import java.util.List;

import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.PsicoComponent;

public interface IMoveHandler {

	boolean move(Direction direction);

	Position getPosition();

	PsicoComponent getBall();

	PsicoComponent dropBall(PsicoComponent ball);

	void setRules(List<GoalRule> rules);

	void setObserver(IRulesObserver rulesObserver);

}
