package br.com.danielhabib.core.rules;

import java.util.List;

import br.com.danielhabib.core.component.Component;
import br.com.danielhabib.core.component.Position;

public interface IGrabbingRules {

	Component getBall(Position position);

	Component dropBall(Position position, Component ball);

	void setGoalRules(List<GoalRule> rules);

	void setObserver(IRulesObserver rulesObserver);

}
