package br.com.danielhabib.core;

public class GoalBuilder extends TypeBuilder {

	public GoalBuilder(ColorBuilder colorBuilder) {
		super(colorBuilder);
	}

	@Override
	protected PsicoComponent newComponent(int x, int y) {
		return new Goal(new Position(x, y));
	}

}
