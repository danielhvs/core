package br.com.danielhabib.core.component;

import java.awt.Graphics;

import br.com.danielhabib.core.Config;

public class Wall extends PsicoComponent {

	public Wall(Position position) {
		super(position);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(position.getX(), position.getY(), Config.SIZE, Config.SIZE);
	}

}
