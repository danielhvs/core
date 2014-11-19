package br.com.danielhabib.core;

public class Psico {

	private IDirectionHandler directionHandler;
	private IMoveHandler moveHandler;

	public Psico(IDirectionHandler handler, IMoveHandler moveHandler) {
		this.directionHandler = handler;
		this.moveHandler = moveHandler;
	}

	public void move() {
		moveHandler.move(directionHandler.getDirection());
	}

	public void turn() {
		directionHandler.turn();
	}

	public Direction getDirection() {
		return directionHandler.getDirection();
	}

	public Position getPosition() {
		return moveHandler.getPosition();
	}

}
