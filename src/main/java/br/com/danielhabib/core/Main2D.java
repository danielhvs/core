package br.com.danielhabib.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JApplet;

public class Main2D extends JApplet {

	private static final long serialVersionUID = -3688474214568402581L;
	private Psico psico;
	private Map<Direction, BufferedImage> images;

	public Main2D(Psico psico) {
		this.psico = psico;
	}

	@Override
	public void init() {
		setBackground(Color.WHITE);
		initImage();
		psico.getPosition();
		new Repainter().start();
	}

	private void initImage() {
		try {
			images = new HashMap<Direction, BufferedImage>();
			String fileBaseName = "/home/danielhabib/projeto/workspace/core/core/resources/psico_";
			for (Direction direction : Direction.values()) {
				String fileNames = fileBaseName.concat(direction.name().toLowerCase());
				images.put(direction, ImageIO.read(new File(fileNames.concat(".png"))));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics g) {
		Position position = psico.getPosition();

		// FIXME: Not working!?
		// g.clearRect(lastPosition.getX(), lastPosition.getY(),
		// image.getWidth(), image.getHeight());
		g.clearRect(0, 0, 550, 550);

		g.drawImage(images.get(psico.getDirection()), position.getX(),
				position.getY(), null);
	}

	private class Repainter extends Thread {

		@Override
		public void run() {
			while (true) {
				sleep();
				repaint();
			}
		}

		private void sleep() {
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}

	}

}