package br.com.danielhabib.core;

import java.awt.Color;
import java.awt.Graphics;

public class Ball implements PsicoComponent {

	private final Position position;

	public Ball(Position position) {
		this.position = position;
	}

	public Position getPosition() {
		return this.position;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Ball other = (Ball) obj;
		if (position == null) {
			if (other.position != null) {
				return false;
			}
		} else if (!position.equals(other.position)) {
			return false;
		}
		return true;
	}

	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		int x = getPosition().getX();
		int y = getPosition().getY();
		int offset = 3 * Config.SIZE / 8;
		int ballSize = Config.SIZE / 4;
		g.fillOval(x + offset, y + offset, ballSize, ballSize);
	}

}
