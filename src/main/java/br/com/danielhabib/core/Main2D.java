package br.com.danielhabib.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JApplet;

public class Main2D extends JApplet {

	private static final long serialVersionUID = -3688474214568402581L;
	private Psico psico;
	private Map<Direction, BufferedImage> images;
	private BufferedImage image;
	private PsicoObserver observer;

	public Main2D(Psico psico) {
		this.psico = psico;
		observer = new PsicoObserver();
		observer.updateLastPosition();
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
			String fileBaseName = "/home/danielhabib/projeto/workspace/core/resources/psico_";
			for (Direction direction : Direction.values()) {
				String fileNames = fileBaseName.concat(direction.name().toLowerCase());
				images.put(direction, ImageIO.read(new File(fileNames.concat(".png"))));
			}
			observer.updateDirection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		Position position = psico.getPosition();
		g.drawImage(image, position.getX(), position.getY(), null);
	}

	class PsicoObserver implements IPsicoObserver {

		public PsicoObserver() {
			updateLastPosition();
		}

		public void positionChanged() {
			repaint();
			updateLastPosition();
		}

		public void updateLastPosition() {
			psico.getPosition();
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