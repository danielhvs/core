package br.com.danielhabib.core.builder;

import br.com.danielhabib.core.component.PsicoComponent;

public abstract class ATypeBuilder {

	private final ColorBuilder colorBuilder;

	public ATypeBuilder(ColorBuilder colorBuilder) {
		this.colorBuilder = colorBuilder;
	}

	public PsicoComponent build(int x, int y) {
		PsicoComponent component = newComponent(x, y);
		component.setColor(colorBuilder.nextColor());
		return component;
	}

	protected abstract PsicoComponent newComponent(int x, int y);
}
