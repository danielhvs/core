package br.com.danielhabib.core.component;

import java.awt.Color;

import br.com.danielhabib.core.gui.Graphics;

public class Ball extends PsicoComponent {

	// Must be even
	public static int OFFSET;
	public static int DIAMETER;
	public static final Color DEFAULT_COLOR = Color.BLUE;
	private int referenceSize;

	public Ball(Position position, int size) {
		super(position, size);
		setColor(DEFAULT_COLOR);
		initSizeParameters(size);
	}

	private void initSizeParameters(int size) {
		this.referenceSize = 2 * size;
		// FIXME: add in beans.xml
		Ball.OFFSET = size / 2;
		Ball.DIAMETER = size;
	}

	@Override
	public void draw(Graphics g) {
		int offset = (referenceSize - getSize()) / 2;
		int x = getPosition().getX()+offset;
		int y = getPosition().getY()+offset;
		g.setColor(color);
		g.fillOval(x, y, getSize(), getSize());
	}

}
