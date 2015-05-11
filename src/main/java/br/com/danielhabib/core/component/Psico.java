package br.com.danielhabib.core.component;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import br.com.danielhabib.core.gui.Graphics;
import br.com.danielhabib.core.nulls.NullComponent;
import br.com.danielhabib.core.nulls.NullObserver;
import br.com.danielhabib.core.rules.AMovingRules;
import br.com.danielhabib.core.rules.IDirectionHandler;
import br.com.danielhabib.core.rules.IGrabbingRules;
import br.com.danielhabib.core.rules.IPsicoObserver;
import br.com.danielhabib.core.rules.ImageHandler;

public class Psico extends Component {

	private static ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/config/beans.xml");
	int i = 0;
	private IDirectionHandler directionHandler;
	private IGrabbingRules grabbingRules;
	private IPsicoObserver observer;
	private Component ball;
	private Integer direction;
	private ImageHandler imageHandler;
	private static final Component NULL_BALL = new NullComponent();
	private AMovingRules movingRules;
	private Map<Integer, Position> speedMap;

	public Psico(IDirectionHandler handler, IGrabbingRules grabbingRules, Position position) {
		super(position, 0);
		this.directionHandler = handler;
		this.grabbingRules = grabbingRules;
		this.observer = new NullObserver();
		this.ball = new NullComponent();
		this.direction = 0;
	}

	public void move() {
		Position initialPosition = getPosition();
		this.position = move(direction);
		if (movedFrom(initialPosition)) {
			ball.setPosition(getPosition());
			setImageHandler(nextImageHandler());
			notifyObserver();
		}
	}

	private ImageHandler nextImageHandler() {
		return context.getBean("imageHandler" + (1 + (++i % 2)), ImageHandler.class);
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
		this.direction = directionHandler.turn(direction);
		setImageHandler(nextImageHandler());
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

}
