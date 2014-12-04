package br.com.danielhabib.core;

public class Wall {
	private Position position;
	private int size;

	public Wall(Position position, int size) {
		this.position = position;
		this.size = size;
	}

	public Position getPosition() {
		return position;
	}

	public int getSize() {
		return size;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + size;
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
		if (size != other.size) {
			return false;
		}
		return true;
	}

}
