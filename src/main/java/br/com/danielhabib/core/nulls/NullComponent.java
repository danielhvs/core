package br.com.danielhabib.core.nulls;

import java.awt.Graphics;

import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.PsicoComponent;

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
