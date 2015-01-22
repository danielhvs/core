package br.com.danielhabib.core.component;

import java.awt.Color;
import java.awt.Graphics;

import br.com.danielhabib.core.Config;

public class Ball extends PsicoComponent {

	// Must be even
	public static int OFFSET = Config.SIZE / 4;
	public static int DIAMETER = Config.SIZE / 2;
	public static final Color DEFAULT_COLOR = Color.BLUE;
	public static final int DEFAULT_SIZE = DIAMETER;

	public Ball(Position position) {
		super(position);
		setColor(DEFAULT_COLOR);
		setSize(DEFAULT_SIZE);
	}

	@Override
	public void draw(Graphics g) {
		int offset = (Config.SIZE - getSize()) / 2;
		int x = getPosition().getX()+offset;
		int y = getPosition().getY()+offset;
		g.setColor(color);
		g.fillOval(x, y, getSize(), getSize());
	}

}
