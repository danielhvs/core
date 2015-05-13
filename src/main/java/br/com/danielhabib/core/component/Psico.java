package br.com.danielhabib.core.component;

import java.util.Map;

import br.com.danielhabib.core.builder.LevelParser;
import br.com.danielhabib.core.gui.Graphics;
import br.com.danielhabib.core.nulls.NullComponent;
import br.com.danielhabib.core.nulls.NullObserver;
import br.com.danielhabib.core.rules.AMovingRules;
import br.com.danielhabib.core.rules.IDirectionHandler;
import br.com.danielhabib.core.rules.IGrabbingRules;
import br.com.danielhabib.core.rules.IPsicoObserver;
import br.com.danielhabib.core.rules.IRulesObserver;
import br.com.danielhabib.core.rules.ImageHandler;

public class Psico extends Component {

	private IDirectionHandler directionRules;
	private IGrabbingRules grabbingRules;
	private IPsicoObserver observer;
	private Component ball;
	private int direction;
	private ImageHandler imageHandler;
	private static final Component NULL_BALL = new NullComponent();
	private AMovingRules movingRules;
	private Map<Integer, Position> speedMap;
	private LevelParser levelParser;

	public Psico() {
		super(new Position(-1, -1), 0);
		this.observer = new NullObserver();
		this.ball = new NullComponent();
	}

	public void move() {
		Position initialPosition = getPosition();
		this.position = move(direction);
		if (movedFrom(initialPosition)) {
			ball.setPosition(position);
			notifyObserver();
		}
	}

	public Position move(Integer direction) {
		return movingRules.move(position, position.add(speedMap.get(direction)));
	}

	private boolean movedFrom(Position initialPosition) {
		return !initialPosition.equals(position);
	}

	public void setObserver(IPsicoObserver observer) {
		this.observer = observer;
	}

	public void turn() {
		this.direction = directionRules.turn(direction);
		setImageHandler(imageHandler);
		notifyObserver();
	}

	public Integer getDirection() {
		return direction;
	}

	private void notifyObserver() {
		observer.hasChanged();
	}

	public void grab() {
		if (!hasBall()) {
			ball = grabbingRules.getBall(position);
			notifyObserver();
		}
	}

	public void drop() {
		if (hasBall()) {
			ball = grabbingRules.dropBall(position, ball);
			notifyObserver();
		}
	}

	private boolean hasBall() {
		return !NULL_BALL.equals(ball);
	}

	public Component getBall() {
		return ball;
	}

	@Override
	public void draw(Graphics g) {
		drawThis(g);
		ball.draw(g);
	}

	private void drawThis(Graphics g) {
		g.drawImage(imageHandler.get(getDirection()), position.getX(), position.getY(), null);
	}

	public void setSpeedMap(Map<Integer, Position> speedMap) {
		this.speedMap = speedMap;
	}

	public void setMovingRules(AMovingRules movingRules) {
		this.movingRules = movingRules;
	}

	public void setImageHandler(ImageHandler imageHandler) {
		this.imageHandler = imageHandler;
	}

	public void build() {
		grabbingRules.setLevelParser(levelParser);
		movingRules.setLevelParser(levelParser);
	}

	public void setDirectionRules(IDirectionHandler directionRules) {
		this.directionRules = directionRules;
	}

	public void setGrabbingRules(IGrabbingRules grabbingRules) {
		this.grabbingRules = grabbingRules;
	}

	public void setLevelParser(LevelParser levelParser) {
		this.levelParser = levelParser;
	}

	public void setMoveHandlerObserver(IRulesObserver iRulesObserver) {
		grabbingRules.setObserver(iRulesObserver);
	}

}
