package br.com.danielhabib.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JApplet;

public class Main2D extends JApplet {

	private static final long serialVersionUID = -3688474214568402581L;
	private Psico psico;
	private Map<Direction, BufferedImage> images;
	private BufferedImage image;
	private PsicoObserver observer;
	private List<PsicoComponent> components;

	public Main2D(Psico psico, Environment env) {
		this.psico = psico;
		this.components = new ArrayList<PsicoComponent>(env.getWalls());
		this.components.addAll(env.getBalls());
		observer = new PsicoObserver();
		psico.setObserver(observer);
	}

	@Override
	public void init() {
		setBackground(Color.WHITE);
		initImage();
	}

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
			observer.updateDirection();
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

	@Override
	public void paint(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		drawComponents(g);
		drawPsico(g);
	}

	private void drawComponents(Graphics g) {
		for (PsicoComponent component : components) {
			component.draw(g);
		}
	}

	private void drawPsico(Graphics g) {
		Position position = psico.getPosition();
		g.drawImage(image, position.getX(), position.getY(), null);
	}

	private BufferedImage resizeImage(BufferedImage originalImage, int type, int size) {
		BufferedImage resizedImage = new BufferedImage(size, size, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, size, size, null);
		g.dispose();
		return resizedImage;
	}

	class PsicoObserver implements IPsicoObserver {

		public void positionChanged() {
			repaint();
		}

		public void directionChanged() {
			updateDirection();
			repaint();
		}

		private void updateDirection() {
			image = images.get(psico.getDirection());
		}

	}

}
