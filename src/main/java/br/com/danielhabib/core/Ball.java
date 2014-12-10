package br.com.danielhabib.core;

import java.awt.Color;
import java.awt.Graphics;

public class Ball extends PsicoComponent {

	private static final int NUMBER_X_OFFSET = -3;
	private static final int NUMBER_Y_OFFSET = 5;

	public Ball(Position position) {
		super(position);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		int x = getPosition().getX();
		int y = getPosition().getY();
		int offset = Config.SIZE / 4;
		int ballSize = Config.SIZE / 2;
		g.fillOval(x + offset, y + offset, ballSize, ballSize);
		g.setColor(Color.YELLOW);
		g.drawString("1", x + offset + NUMBER_X_OFFSET + ballSize / 2, y + offset + NUMBER_Y_OFFSET + ballSize / 2);
	}

}
