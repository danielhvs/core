package br.com.danielhabib.core.builder;

import br.com.danielhabib.core.Ball;
import br.com.danielhabib.core.Position;
import br.com.danielhabib.core.PsicoComponent;

public class BallBuilder extends ATypeBuilder {

	public BallBuilder(ColorBuilder colorBuilder) {
		super(colorBuilder);
	}

	@Override
	protected PsicoComponent newComponent(int x, int y) {
		return new Ball(new Position(x, y));
	}

}
