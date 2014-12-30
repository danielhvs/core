package br.com.danielhabib.core;

public class WallBuilder extends TypeBuilder {

	public WallBuilder(ColorBuilder colorBuilder) {
		super(colorBuilder);
	}

	@Override
	public PsicoComponent newComponent(int x, int y) {
		return new Wall(new Position(x, y));
	}

}
