package br.com.danielhabib.core.rules;

import br.com.danielhabib.core.component.Position;

public class MovingRulesThroughWall extends AMovingRules {

	@Override
	public Position move(Position initialPosition, Position nextPosition) {
		return nextPosition;
	}

}
