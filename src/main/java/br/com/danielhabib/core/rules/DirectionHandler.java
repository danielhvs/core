package br.com.danielhabib.core.rules;

import java.util.HashMap;
import java.util.Map;

public class DirectionHandler implements IDirectionHandler {

	private Direction direction;
	private Map<Direction, Direction> directionsMap = new HashMap<Direction, Direction>();

	public void turn() {
		direction = directionsMap.get(direction);
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public void setDirectionsMap(Map<Direction, Direction> directionsMap) {
		this.directionsMap = directionsMap;
	}
}
