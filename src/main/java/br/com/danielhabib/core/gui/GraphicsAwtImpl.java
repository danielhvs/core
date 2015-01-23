package br.com.danielhabib.core.gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.ImageObserver;

public class GraphicsAwtImpl implements Graphics {

	private final java.awt.Graphics g;

	public GraphicsAwtImpl(java.awt.Graphics g) {
		this.g = g;
	}

	public void setColor(Color color) {
		g.setColor(color);
	}

	public void fillOval(int x, int y, int sizeX, int sizeY) {
		g.fillOval(x, y, sizeX, sizeY);
	}

	public void drawRect(int x, int y, int sizeX, int sizeY) {
		g.drawRect(x, y, sizeX, sizeY);
	}

	public void drawImage(Image image, int x, int y, ImageObserver object) {
		g.drawImage(image, x, y, object);
	}

	public void drawString(String string, int x, int y) {
		g.drawString(string, x, y);
	}

	public void fillRect(int x, int y, int sizeX, int sizeY) {
		g.fillRect(x, y, sizeX, sizeY);
	}

}
