package br.com.danielhabib.core;

public class Position {
	private int x;
	private int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void add(Position position) {
		x += position.getX();
		y += position.getY();
	}

	@Override
	public String toString() {
		return new StringBuilder("x = ").append(x).append("; y = ").append(y).toString();
	}
}
