package br.com.danielhabib.core.component;

import java.awt.Color;

import br.com.danielhabib.core.gui.Graphics;

public abstract class Component {
	protected Position position;
	protected Color color;
	private int size;

	public Component(Position position, int size) {
		this.position = position;
		this.size = size;
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

	public abstract void draw(Graphics g);

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
		Component other = (Component) obj;
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
