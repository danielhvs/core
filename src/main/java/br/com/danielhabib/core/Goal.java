package br.com.danielhabib.core;

import java.awt.Graphics;

public class Goal extends PsicoComponent {

	public Goal(Position position) {
		super(position);
	}

	@Override
	protected void draw(Graphics g) {
		g.setColor(color);
		g.drawRect(position.getX(), position.getY(), Config.SIZE, Config.SIZE);
	}

}
