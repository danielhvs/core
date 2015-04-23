package br.com.danielhabib.core.rules;

import br.com.danielhabib.core.component.Position;

public class MovingRules extends AMovingRules {

	private boolean canMove(Position nextPosition) {
		return !levelParser.hasWall(nextPosition);
	}

	@Override
	public Position move(Position initialPosition, Position nextPosition) {
		return canMove(nextPosition) ? nextPosition : initialPosition;
	}

}
