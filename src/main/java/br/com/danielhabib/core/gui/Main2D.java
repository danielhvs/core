package br.com.danielhabib.core.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JApplet;

import br.com.danielhabib.core.builder.LevelParser;
import br.com.danielhabib.core.component.Psico;
import br.com.danielhabib.core.rules.IPsicoObserver;

public class Main2D extends JApplet {

	private static final long serialVersionUID = -3688474214568402581L;
	private Psico psico;
	private LevelParser parser;

	public Main2D(Psico psico, LevelParser parser) {
		this.psico = psico;
		this.parser = parser;
		psico.setObserver(new IPsicoObserver() {
			public void hasChanged() {
				repaint();
			}
		});
	}

	@Override
	public void init() {
		setBackground(Color.WHITE);
	}

	@Override
	public void paint(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		br.com.danielhabib.core.gui.Graphics awt = GraphicsAdapter.fromAwt(g);
		parser.draw(awt);
		psico.draw(awt);
	}

}
