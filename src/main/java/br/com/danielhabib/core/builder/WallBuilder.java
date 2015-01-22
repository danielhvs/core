package br.com.danielhabib.core.builder;

import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.PsicoComponent;
import br.com.danielhabib.core.component.Wall;

public class WallBuilder extends ATypeBuilder {

	public WallBuilder(ColorBuilder colorBuilder) {
		super(colorBuilder);
	}

	@Override
	public PsicoComponent newComponent(int x, int y) {
		return new Wall(new Position(x, y));
	}

}
