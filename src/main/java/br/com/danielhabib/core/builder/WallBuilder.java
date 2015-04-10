package br.com.danielhabib.core.builder;

import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.PsicoComponent;
import br.com.danielhabib.core.component.Wall;

public class WallBuilder extends ATypeBuilder {

	@Override
	public PsicoComponent newComponent(Position position) {
		return new Wall(position, size);
	}

}
