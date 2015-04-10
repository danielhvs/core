package br.com.danielhabib.core.builder;

import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.PsicoComponent;

public abstract class ATypeBuilder {
	private ColorBuilder colorBuilder;
	public void setColorBuilder(ColorBuilder colorBuilder) {
		this.colorBuilder = colorBuilder;
	}

	protected int size;
	public void setSize(int size) {
		this.size = size;
	}

	public PsicoComponent build(Position position) {
		PsicoComponent component = newComponent(position);
		component.setColor(colorBuilder.nextColor());
		return component;
	}

	protected abstract PsicoComponent newComponent(Position position);
}
