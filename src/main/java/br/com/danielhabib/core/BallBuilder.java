package br.com.danielhabib.core;

public class BallBuilder extends TypeBuilder {

	public BallBuilder(ColorBuilder colorBuilder) {
		super(colorBuilder);
	}

	@Override
	protected PsicoComponent newComponent(int x, int y) {
		return new Ball(new Position(x, y));
	}

}
