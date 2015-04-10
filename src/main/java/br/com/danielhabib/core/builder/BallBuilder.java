package br.com.danielhabib.core.builder;

import br.com.danielhabib.core.component.Ball;
import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.PsicoComponent;

public class BallBuilder extends ATypeBuilder {

	public BallBuilder(ColorBuilder colorBuilder) {
		super(colorBuilder);
	}

	@Override
	protected PsicoComponent newComponent(Position position) {
		return new Ball(position);
	}

}
