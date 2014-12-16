package br.com.danielhabib.core;

import java.awt.Color;
import java.awt.Graphics;

public class Ball extends PsicoComponent {

	// Must be even
	public static final int INSIDE_DIAMETER_OFFSET = 4;
	public static final int INSIDE_OFFSET = INSIDE_DIAMETER_OFFSET / 2;
	public static int OFFSET = Config.SIZE / 4;
	public static int DIAMETER = Config.SIZE / 2;

	public Ball(Position position) {
		super(position);
	}

	@Override
	public void draw(Graphics g) {
		int x = getPosition().getX();
		int y = getPosition().getY();
		g.setColor(Color.BLUE);
		g.fillOval(x + OFFSET, y + OFFSET, DIAMETER, DIAMETER);
	}

}
