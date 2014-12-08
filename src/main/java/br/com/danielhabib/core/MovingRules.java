package br.com.danielhabib.core;

import java.util.List;

public class MovingRules {

	private List<PsicoComponent> walls;

	public MovingRules(Environment environment) {
		this.walls = environment.getWalls();
	}

	public boolean canMove(Position position) {
		for (PsicoComponent wall : walls) {
			if (wall.getPosition().equals(position)) {
				return false;
			}
		}
		return true;
	}

}
