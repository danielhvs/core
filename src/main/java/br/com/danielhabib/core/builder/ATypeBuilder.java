package br.com.danielhabib.core.builder;

import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.PsicoComponent;

public abstract class ATypeBuilder {

	private final ColorBuilder colorBuilder;

	public ATypeBuilder(ColorBuilder colorBuilder) {
		this.colorBuilder = colorBuilder;
	}

	public PsicoComponent build(Position position) {
		PsicoComponent component = newComponent(position);
		component.setColor(colorBuilder.nextColor());
		return component;
	}

	protected abstract PsicoComponent newComponent(Position position);
}
