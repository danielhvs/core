package br.com.danielhabib.core;

import java.awt.Graphics;

public class Psico extends PsicoComponent {

	private IDirectionHandler directionHandler;
	private IMoveHandler moveHandler;
	private IPsicoObserver observer;

	public void setObserver(IPsicoObserver observer) {
		this.observer = observer;
	}

	private PsicoComponent ball;
	private ImageHandler imageHandler;
	private static final PsicoComponent NULL_BALL = new NullComponent();

	public Psico(IDirectionHandler handler, IMoveHandler moveHandler, ImageHandler imageHandler) {
		super(moveHandler.getPosition());
		this.directionHandler = handler;
		this.moveHandler = moveHandler;
		this.observer = new NullObserver();
		this.ball = new NullComponent();
		this.imageHandler = imageHandler;

		imageHandler.initImage(getDirection());
	}

	public void move() {
		boolean moved = moveHandler.move(directionHandler.getDirection());
		if (moved) {
			ball.setPosition(getPosition());
			notifyObserver();
		}
	}

	public void turn() {
		directionHandler.turn();
		notifyObserver();
	}

	public Direction getDirection() {
		return directionHandler.getDirection();
	}

	@Override
	public Position getPosition() {
		return moveHandler.getPosition();
	}

	private void notifyObserver() {
		observer.hasChanged();
	}

	public void grab() {
		if (!hasBall()) {
			ball = moveHandler.getBall();
			notifyObserver();
		}
	}

	public void drop() {
		if (hasBall()) {
			ball = moveHandler.dropBall();
			notifyObserver();
		}
	}

	private boolean hasBall() {
		return !NULL_BALL.equals(ball);
	}

	public PsicoComponent getBall() {
		return ball;
	}

	@Override
	void draw(Graphics g) {
		drawThis(g);
		ball.draw(g);
	}

	private void drawThis(Graphics g) {
		Position position = getPosition();
		g.drawImage(imageHandler.get(getDirection()), position.getX(), position.getY(), null);
	}

}
