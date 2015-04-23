package br.com.danielhabib.core.builder;

import br.com.danielhabib.core.component.Ball;
import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.Component;

public class BallBuilder extends ATypeBuilder {

	@Override
	protected Component newComponent(Position position) {
		return new Ball(position, size);
	}

}
