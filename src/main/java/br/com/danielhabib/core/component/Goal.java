package br.com.danielhabib.core.component;


import br.com.danielhabib.core.gui.Graphics;

public class Goal extends Component {

	public Goal(Position position, int size) {
		super(position, size);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.drawRect(position.getX(), position.getY(), getSize(), getSize());
	}

	@Override
	public String toString() {
		return "G: " + position;
	}

}
