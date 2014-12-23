package br.com.danielhabib.core;

import java.awt.Graphics;
import java.util.Stack;

public class PsicoComponentContainer extends PsicoComponent {

	private Stack<PsicoComponent> balls;

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
		if (!balls.isEmpty()) {
			if(balls.size()==1) {
				balls.peek().draw(g);
			} else {
				int diameter = Ball.DIAMETER;
				int i = 0;
				for (PsicoComponent ball : balls) {
					int x = ball.getPosition().getX() + i * Ball.INSIDE_OFFSET;
					int y = ball.getPosition().getY() + i * Ball.INSIDE_OFFSET;
					g.setColor(ball.getColor());
					g.fillOval(x + Ball.OFFSET, y + Ball.OFFSET, diameter, diameter);
					diameter -= Ball.INSIDE_DIAMETER_OFFSET;
					i++;
				}
			}
		}
	}

	public PsicoComponent pop() {
		return balls.isEmpty() ? new NullComponent() : balls.pop();
	}

	public void push(PsicoComponent component) {
		balls.push(component);
	}

	public int size() {
		return balls.size();
	}

	public boolean isEmpty() {
		return balls.isEmpty();
	}

}
