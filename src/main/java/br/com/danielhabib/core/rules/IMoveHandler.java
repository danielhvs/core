package br.com.danielhabib.core.rules;

import java.util.List;

import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.Component;

public interface IMoveHandler {

	boolean move(Integer direction);

	Position getPosition();

	Component getBall();

	Component dropBall(Component ball);

	void setRules(List<GoalRule> rules);

	void setObserver(IRulesObserver rulesObserver);

}
