package br.com.danielhabib.core;

import java.awt.Color;

public class ColorBuilder implements IColorBuilder {

	private Color[] colors;
	private int index;

	public ColorBuilder(Color[] colors) {
		this.colors = colors;
		this.index = -1;
	}

	public Color nextColor() {
		return colors[nextIndex()];
	}

	private int nextIndex() {
		index = (index + 1) % colors.length;
		return index;
	}


}
