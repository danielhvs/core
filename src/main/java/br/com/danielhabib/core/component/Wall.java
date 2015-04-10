package br.com.danielhabib.core.component;


import br.com.danielhabib.core.Config;
import br.com.danielhabib.core.gui.Graphics;

public class Wall extends PsicoComponent {

	public Wall(Position position, int size) {
		super(position, size);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(position.getX(), position.getY(), Config.SIZE, Config.SIZE);
	}

	@Override
	public String toString() {
		return position.getX() + "," + position.getY();
	}

}
