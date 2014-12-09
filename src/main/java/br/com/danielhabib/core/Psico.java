package br.com.danielhabib.core;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Psico extends PsicoComponent {

	private IDirectionHandler directionHandler;
	private IMoveHandler moveHandler;
	private IPsicoObserver observer;

	public void setObserver(IPsicoObserver observer) {
		this.observer = observer;
	}

	private PsicoComponent ball;

	public Psico(IDirectionHandler handler, IMoveHandler moveHandler) {
		super(moveHandler.getPosition());
		this.directionHandler = handler;
		this.moveHandler = moveHandler;
		this.observer = new NullObserver();
		this.ball = new NullComponent();
		this.initImage();
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
		image = images.get(getDirection());
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
		if (moveHandler.hasBall()) {
			ball = moveHandler.getBall();
			notifyObserver();
		}
	}

	public void drop() {
		ball = moveHandler.dropBall();
		notifyObserver();
	}

	public PsicoComponent getBall() {
		return ball;
	}

	private Map<Direction, BufferedImage> images;
	private BufferedImage image;

	@Override
	void draw(Graphics g) {
		drawThis(g);
		ball.draw(g);
	}

	private void drawThis(Graphics g) {
		Position position = getPosition();
		g.drawImage(image, position.getX(), position.getY(), null);
	}

	// TODO: Refactor. Too much init code in here...
	private void initImage() {
		try {
			images = new HashMap<Direction, BufferedImage>();
			String fileBaseName = "psico_";
			for (Direction direction : Direction.values()) {
				String fileName = fileBaseName.concat(direction.name().toLowerCase()).concat(".png");
				InputStream inputStream = readResource(fileName);
				BufferedImage originalImage = ImageIO.read(inputStream);
				images.put(direction, resizeImage(originalImage, originalImage.getType(), Config.SIZE));
			}
			image = images.get(getDirection());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private InputStream readResource(String fileName) {
		final ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream stream = loader.getResourceAsStream(fileName);
		if (stream == null) {
			stream = getClass().getClassLoader().getResourceAsStream(fileName);
			if (stream == null) {
				stream = getClass().getResourceAsStream(fileName);
			}
		}
		return stream;

	}

	private BufferedImage resizeImage(BufferedImage originalImage, int type, int size) {
		BufferedImage resizedImage = new BufferedImage(size, size, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, size, size, null);
		g.dispose();
		return resizedImage;
	}

}
