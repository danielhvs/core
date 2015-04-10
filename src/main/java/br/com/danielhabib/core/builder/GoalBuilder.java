package br.com.danielhabib.core.builder;

import br.com.danielhabib.core.component.Goal;
import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.PsicoComponent;

public class GoalBuilder extends ATypeBuilder {

	@Override
	protected PsicoComponent newComponent(Position position) {
		return new Goal(position, size);
	}

}
