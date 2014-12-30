package br.com.danielhabib.core;

import java.awt.Graphics;

public class NullComponent extends PsicoComponent {

	public NullComponent() {
		super(new Position(-1, -1));
	}

	@Override
	public void draw(Graphics g) {
	}

	@Override
	public void setPosition(Position position) {
	}

	@Override
	public void setSize(int size) {
	}

}
