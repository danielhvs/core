package br.com.danielhabib.core.rules;

public enum Direction {
	UP(90), DOWN(270), LEFT(180), RIGHT(0);
	private final int angle;

	Direction(int angle) {
		this.angle = angle;
	}

	public int getAngle() {
		return angle;
	}
}
