package br.com.danielhabib.core.builder;

import br.com.danielhabib.core.Goal;
import br.com.danielhabib.core.Position;
import br.com.danielhabib.core.PsicoComponent;

public class GoalBuilder extends ATypeBuilder {

	public GoalBuilder(ColorBuilder colorBuilder) {
		super(colorBuilder);
	}

	@Override
	protected PsicoComponent newComponent(int x, int y) {
		return new Goal(new Position(x, y));
	}

}
