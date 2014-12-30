package br.com.danielhabib.core.nulls;

import java.awt.Color;

import br.com.danielhabib.core.builder.IColorBuilder;

public class NullColorBuilder implements IColorBuilder {

	public Color nextColor() {
		return null;
	}

}
