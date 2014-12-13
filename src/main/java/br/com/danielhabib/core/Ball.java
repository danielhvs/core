package br.com.danielhabib.core;

import java.awt.Color;
import java.awt.Graphics;

public class Ball extends PsicoComponent {

	// Must be even
	private static final int INSIDE_DIAMETER_OFFSET = 8;
	private static final int INSIDE_OFFSET = INSIDE_DIAMETER_OFFSET / 2;
	public static int OFFSET = Config.SIZE / 4;
	public static int DIAMETER = Config.SIZE / 2;

	public Ball(Position position) {
		super(position);
	}

	@Override
	public void draw(Graphics g) {
		int x = getPosition().getX();
		int y = getPosition().getY();
		g.setColor(Color.RED);
		g.fillOval(x + OFFSET, y + OFFSET, DIAMETER, DIAMETER);
		g.setColor(Color.BLUE);
		g.fillOval(x + OFFSET + INSIDE_OFFSET, y + OFFSET + INSIDE_OFFSET, DIAMETER - INSIDE_DIAMETER_OFFSET, DIAMETER
				- INSIDE_DIAMETER_OFFSET);
	}

}
