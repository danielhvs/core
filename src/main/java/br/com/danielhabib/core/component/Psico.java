package br.com.danielhabib.core.component;

import br.com.danielhabib.core.gui.Graphics;
import br.com.danielhabib.core.nulls.NullComponent;
import br.com.danielhabib.core.nulls.NullObserver;
import br.com.danielhabib.core.rules.IDirectionHandler;
import br.com.danielhabib.core.rules.IMoveHandler;
import br.com.danielhabib.core.rules.IPsicoObserver;
import br.com.danielhabib.core.rules.ImageHandler;

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
		// FIXME: Maybe this is not a "PsicoComponent"
		super(moveHandler.getPosition(), 0);
		this.directionHandler = handler;
		this.moveHandler = moveHandler;
		this.observer = new NullObserver();
		this.ball = new NullComponent();
		this.imageHandler = imageHandler;
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

	public Integer getDirection() {
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
			ball = moveHandler.dropBall(ball);
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
	public void draw(Graphics g) {
		drawThis(g);
		ball.draw(g);
	}

	private void drawThis(Graphics g) {
		Position position = getPosition();
		g.drawImage(imageHandler.get(getDirection()), position.getX(), position.getY(), null);
	}

}
