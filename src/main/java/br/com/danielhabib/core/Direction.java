package br.com.danielhabib.core;

public enum Direction {
	UP(0, 1), DOWN(0, -1), LEFT(-1, 0), RIGHT(1, 0);

	private int incX;

	public int getIncX() {
		return incX;
	}

	public int getIncY() {
		return incY;
	}

	private int incY;

	private Direction(int incX, int incY) {
		this.incX = incX;
		this.incY = incY;
	}
}
