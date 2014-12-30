package br.com.danielhabib.core;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Stack;

public class PsicoComponentContainer extends PsicoComponent {

	private Stack<PsicoComponent> balls;
	private int smallerBallSize = 0;
	public static final int INSIDE_DIAMETER_OFFSET = 4;
	private static final int NUMBER_X_OFFSET = -3;
	private static final int NUMBER_Y_OFFSET = 5;

	public PsicoComponentContainer(Position position) {
		super(position);
		this.balls = new Stack<PsicoComponent>();
	}

	public PsicoComponentContainer(PsicoComponent ball) {
		super(ball.getPosition());
		this.balls = new Stack<PsicoComponent>();
		push(ball);
	}
	@Override
	void draw(Graphics g) {
		for (PsicoComponent ball : balls) {
			ball.draw(g);
		}
		if (!balls.isEmpty()) {
			drawLabel(g);
		}
	}

	public void drawLabel(Graphics g) {
		g.setColor(Color.BLACK);
		int quantity = balls.size();
		int x = position.getX() + Ball.OFFSET + xOffset(quantity) + Ball.DIAMETER / 2;
		int y = position.getY() + Ball.OFFSET + NUMBER_Y_OFFSET + Ball.DIAMETER / 2;
		g.drawString(String.valueOf(quantity), x, y);
	}

	private int xOffset(int count) {
		int numDigits = String.valueOf(count).length();
		return NUMBER_X_OFFSET - 4 * (numDigits - 1);
	}

	public PsicoComponent pop() {
		return balls.isEmpty() ? new NullComponent() : popWithDefaultSize();
	}

	private PsicoComponent popWithDefaultSize() {
		PsicoComponent ball = balls.pop();
		ball.setSize(Ball.DEFAULT_SIZE);
		smallerBallSize += INSIDE_DIAMETER_OFFSET;
		return ball;
	}

	public void push(PsicoComponent component) {
		if (balls.size() > 0) {
			component.setSize(smallerBallSize - INSIDE_DIAMETER_OFFSET);
		}
		smallerBallSize = component.getSize();
		balls.push(component);
	}

	public boolean isEmpty() {
		return balls.isEmpty();
	}

	public PsicoComponent get(int i) {
		return balls.get(i);
	}

}
