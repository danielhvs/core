package br.com.danielhabib.core;

import java.awt.Graphics;
import java.util.Stack;

public class PsicoComponentContainer extends PsicoComponent {

	private Stack<PsicoComponent> balls;
	private int smallerBallSize = 0;
	public static final int INSIDE_DIAMETER_OFFSET = 4;

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
	}

	public PsicoComponent pop() {
		return balls.isEmpty() ? new NullComponent() : popWithSize(Ball.DEFAULT_SIZE);
	}

	private PsicoComponent popWithSize(int defaultSize) {
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

	public int size() {
		return balls.size();
	}

	public boolean isEmpty() {
		return balls.isEmpty();
	}

	public PsicoComponent get(int i) {
		return balls.get(i);
	}

}
