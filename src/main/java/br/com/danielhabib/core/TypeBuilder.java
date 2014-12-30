package br.com.danielhabib.core;

public abstract class TypeBuilder {

	private final ColorBuilder colorBuilder;

	public TypeBuilder(ColorBuilder colorBuilder) {
		this.colorBuilder = colorBuilder;
	}

	public PsicoComponent build(int x, int y) {
		PsicoComponent component = newComponent(x, y);
		component.setColor(colorBuilder.nextColor());
		return component;
	}

	protected abstract PsicoComponent newComponent(int x, int y);
}
