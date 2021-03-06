package br.com.danielhabib.core.component;

import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Position {
	private static final int SIZE = new FileSystemXmlApplicationContext("src/main/resources/config/beans.xml").getBean("configSize", Integer.class);

	private int x;
	private int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Position(String string) {
		String[] tokens = string.split(",");
		this.x = Integer.parseInt(tokens[0].trim());
		this.y = Integer.parseInt(tokens[1].trim());
	}

	public int getX() {
		return x * SIZE;
	}

	public int getY() {
		return y * SIZE;
	}

	public int x() {
		return x;
	}

	public int y() {
		return y;
	}

	public Position add(Position position) {
		return new Position(x + position.x(), y + position.y());
	}

	@Override
	public String toString() {
		return new StringBuilder("(").append(x).append(", ").append(y).append(")").toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Position other = (Position) obj;
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}

}
