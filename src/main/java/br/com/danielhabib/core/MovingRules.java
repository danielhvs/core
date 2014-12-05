package br.com.danielhabib.core;

import java.util.List;

public class MovingRules {

	private List<Wall> walls;

	public MovingRules(Environment environment) {
		this.walls = environment.getWalls();
	}

	public boolean canMove(Position position) {
		for (Wall wall : walls) {
			if (wall.getPosition().equals(position)) {
				return false;
			}
		}
		return true;
	}

}
