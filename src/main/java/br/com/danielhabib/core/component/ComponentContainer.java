package br.com.danielhabib.core.component;

import java.awt.Color;
import java.util.Stack;

import br.com.danielhabib.core.gui.Graphics;
import br.com.danielhabib.core.nulls.NullComponent;

public class ComponentContainer extends Component {

	private Stack<Component> balls;
	private int smallerBallSize = 0;
	public static final int INSIDE_DIAMETER_OFFSET = 4;
	private static final int NUMBER_X_OFFSET = -3;
	private static final int NUMBER_Y_OFFSET = 5;

	public ComponentContainer(Position position) {
		super(position, 0);
		this.balls = new Stack<Component>();
	}

	public ComponentContainer(Component ball) {
		super(ball.getPosition(), 0);
		this.balls = new Stack<Component>();
		push(ball);
	}

	@Override
	public void draw(Graphics g) {
		for (Component ball : balls) {
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

	public Component pop() {
		return balls.isEmpty() ? new NullComponent() : popWithDefaultSize();
	}

	private Component popWithDefaultSize() {
		Component ball = balls.pop();
		ball.setSize(Ball.DIAMETER);
		smallerBallSize += INSIDE_DIAMETER_OFFSET;
		return ball;
	}

	public void push(Component component) {
		if (balls.size() > 0) {
			component.setSize(smallerBallSize - INSIDE_DIAMETER_OFFSET);
		}
		smallerBallSize = component.getSize();
		balls.push(component);
	}

	public boolean isEmpty() {
		return balls.isEmpty();
	}

	public Component get(int i) {
		return balls.get(i);
	}

}
