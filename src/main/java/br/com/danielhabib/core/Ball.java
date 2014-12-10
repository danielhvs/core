package br.com.danielhabib.core;

import java.awt.Color;
import java.awt.Graphics;

public class Ball extends PsicoComponent {

	public Ball(Position position) {
		super(position);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		int x = getPosition().getX();
		int y = getPosition().getY();
		int offset = 3 * Config.SIZE / 8;
		int ballSize = Config.SIZE / 4;
		g.fillOval(x + offset, y + offset, ballSize, ballSize);
		g.setColor(Color.YELLOW);
		g.drawString("1", x + offset + ballSize / 3, y + offset + 9 * ballSize / 10);
	}

}
