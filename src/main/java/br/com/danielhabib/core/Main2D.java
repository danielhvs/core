package br.com.danielhabib.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JApplet;

public class Main2D extends JApplet {

	private static final long serialVersionUID = -3688474214568402581L;
	private ImageIcon image;
	private Psico psico;
	private Position lastPosition;

	public Main2D(Psico psico) {
		this.psico = psico;
	}

	@Override
	public void init() {
		setBackground(Color.WHITE);
		initImage();
		this.lastPosition = psico.getPosition();
		new Repainter().start();
	}

	private void initImage() {
		try {
			BufferedImage bufferedImage = ImageIO
					.read(new File(
							"/home/danielhabib/projeto/workspace/core/core/resources/psico.png"));
			image = new ImageIcon(bufferedImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics g) {
		Position position = psico.getPosition();

		// FIXME: Not working!?
		g.clearRect(lastPosition.getX(), lastPosition.getY(), image.getIconWidth(), image.getIconHeight());

		g.drawImage(image.getImage(), position.getX(), position.getY(), null);
		this.lastPosition = position;
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