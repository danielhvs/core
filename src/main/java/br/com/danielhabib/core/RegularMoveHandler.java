package br.com.danielhabib.core;

public class RegularMoveHandler implements IMoveHandler {

	private Position position;

	public RegularMoveHandler(Position position) {
		this.position = position;
	}

	public Position getPosition() {
		return position;
	}

	public void move(Direction direction) {
		position.incrementX(direction.getIncX());
		position.incrementY(direction.getIncY());
	}
}
