package br.com.danielhabib.core;

import java.awt.Color;
import java.awt.Graphics;

public class Wall implements PsicoComponent {
	private Position position;

	public Wall(Position position) {
		this.position = position;
	}

	public Position getPosition() {
		return position;
	}

	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(position.getX(), position.getY(), Config.SIZE, Config.SIZE);
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
		Wall other = (Wall) obj;
		if (position == null) {
			if (other.position != null) {
				return false;
			}
		} else if (!position.equals(other.position)) {
			return false;
		}
		return true;
	}

}
