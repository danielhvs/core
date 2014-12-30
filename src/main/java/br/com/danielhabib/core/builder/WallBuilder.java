package br.com.danielhabib.core.builder;

import br.com.danielhabib.core.Position;
import br.com.danielhabib.core.PsicoComponent;
import br.com.danielhabib.core.Wall;

public class WallBuilder extends ATypeBuilder {

	public WallBuilder(ColorBuilder colorBuilder) {
		super(colorBuilder);
	}

	@Override
	public PsicoComponent newComponent(int x, int y) {
		return new Wall(new Position(x, y));
	}

}
