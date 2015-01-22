package br.com.danielhabib.core.builder;

import br.com.danielhabib.core.component.Ball;
import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.PsicoComponent;

public class BallBuilder extends ATypeBuilder {

	public BallBuilder(ColorBuilder colorBuilder) {
		super(colorBuilder);
	}

	@Override
	protected PsicoComponent newComponent(int x, int y) {
		return new Ball(new Position(x, y));
	}

}
