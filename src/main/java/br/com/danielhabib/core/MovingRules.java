package br.com.danielhabib.core;

import java.util.List;

public class MovingRules {

	private Environment env;

	public MovingRules(Environment environment) {
		this.env = environment;
	}

	public boolean canMove(Position position) {
		return !hasComponentAt(position, env.getWalls());
	}

	public boolean hasBall(Position position) {
		return hasComponentAt(position, env.getBalls());
	}

	public PsicoComponent getBall(Position position) {
		PsicoComponent ball = getBallAtPosition(position);
		env.removeBall(ball);
		return ball;
	}

	private PsicoComponent getBallAtPosition(Position position) {
		for (PsicoComponent ball : env.getBalls()) {
			if (ball.getPosition().equals(position)) {
				return ball;
			}
		}
		return new NullComponent();
	}

	private boolean hasComponentAt(Position position, List<PsicoComponent> components) {
		for (PsicoComponent component : components) {
			if (component.getPosition().equals(position)) {
				return true;
			}
		}
		return false;
	}

	public void dropBall(Position position) {
		env.getBalls().add(new Ball(position));
	}

}
