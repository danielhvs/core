package br.com.danielhabib.core;

import java.awt.Color;
import java.awt.Graphics;

public abstract class PsicoComponent {
	protected Position position;
	protected Color color;
	private int size;

	public PsicoComponent(Position position) {
		this.position = position;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Position getPosition(){
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Color getColor() {
		return color;
	}

	public int getSize() {
		return size;
	}

	protected abstract void draw(Graphics g);

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((position == null) ? 0 : position.hashCode());
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
		PsicoComponent other = (PsicoComponent) obj;
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
