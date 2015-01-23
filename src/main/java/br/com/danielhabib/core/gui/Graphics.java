package br.com.danielhabib.core.gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.ImageObserver;

public interface Graphics {

	public void setColor(Color color);

	public void fillOval(int x, int y, int sizeX, int sizeY);

	public void drawRect(int x, int y, int sizeX, int sizeY);

	public void drawImage(Image image, int x, int y, ImageObserver object);

	public void drawString(String string, int x, int y);

	public void fillRect(int x, int y, int sizeX, int sizeY);

}
