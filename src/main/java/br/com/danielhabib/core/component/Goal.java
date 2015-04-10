package br.com.danielhabib.core.component;


import br.com.danielhabib.core.Config;
import br.com.danielhabib.core.gui.Graphics;

public class Goal extends PsicoComponent {

	public Goal(Position position, int size) {
		super(position, size);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.drawRect(position.getX(), position.getY(), Config.SIZE, Config.SIZE);
	}

}
