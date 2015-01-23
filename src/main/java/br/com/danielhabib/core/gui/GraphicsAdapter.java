package br.com.danielhabib.core.gui;

public class GraphicsAdapter {
	public static Graphics fromAwt(java.awt.Graphics g) {
		return new GraphicsAwtImpl(g);
	}
}
