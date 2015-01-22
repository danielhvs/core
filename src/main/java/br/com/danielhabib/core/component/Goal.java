package br.com.danielhabib.core.component;

import java.awt.Graphics;

import br.com.danielhabib.core.Config;

public class Goal extends PsicoComponent {

	public Goal(Position position) {
		super(position);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.drawRect(position.getX(), position.getY(), Config.SIZE, Config.SIZE);
	}

}
