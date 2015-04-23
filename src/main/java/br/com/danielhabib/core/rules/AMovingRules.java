package br.com.danielhabib.core.rules;

import br.com.danielhabib.core.builder.LevelParser;
import br.com.danielhabib.core.component.Position;

public abstract class AMovingRules {
	public abstract Position move(Position initialPosition, Position nextPosition);

	protected LevelParser levelParser;

	public void setLevelParser(LevelParser levelParser) {
		this.levelParser = levelParser;
	}
}
