package br.com.danielhabib.core;

import java.awt.Color;
import java.awt.Graphics;

public class Ball extends PsicoComponent {

	public static int OFFSET = Config.SIZE / 4;
	public static int DIAMETER = Config.SIZE / 2;

	public Ball(Position position) {
		super(position);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		int x = getPosition().getX();
		int y = getPosition().getY();
		g.fillOval(x + OFFSET, y + OFFSET, DIAMETER, DIAMETER);
	}

}
