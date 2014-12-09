package br.com.danielhabib.core;

import java.awt.Color;
import java.awt.Graphics;

public class Wall extends PsicoComponent {

	public Wall(Position position) {
		super(position);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(position.getX(), position.getY(), Config.SIZE, Config.SIZE);
	}

}
