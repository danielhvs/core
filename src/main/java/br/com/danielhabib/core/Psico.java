package br.com.danielhabib.core;

public class Psico {

	private IDirectionHandler directionHandler;
	private IMoveHandler moveHandler;
	private IPsicoObserver observer;

	public Psico(IDirectionHandler handler, IMoveHandler moveHandler) {
		this.directionHandler = handler;
		this.moveHandler = moveHandler;
		this.observer = new NullObserver();
	}

	public void move() {
		moveHandler.move(directionHandler.getDirection());
		observer.positionChanged();
	}

	public void turn() {
		directionHandler.turn();
		observer.directionChanged();
	}

	public Direction getDirection() {
		return directionHandler.getDirection();
	}

	public Position getPosition() {
		return moveHandler.getPosition();
	}

	public void setObserver(IPsicoObserver observer) {
		this.observer = observer;
	}

	public void setSpeed(int speed) {
		moveHandler.setSpeed(speed);
	}

}
