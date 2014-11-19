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

	public void incrementX(int incX) {
		x += incX;
	}

	public void incrementY(int incY) {
		y += incY;
	}
}
