package br.com.danielhabib.core;

import java.awt.Color;

public class NullColorBuilder implements IColorBuilder {

	public Color nextColor() {
		return null;
	}

}
