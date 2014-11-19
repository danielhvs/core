package br.com.danielhabib.core;

import java.util.HashMap;
import java.util.Map;

public class CounterClockWiseDirection implements IDirectionHandler {
	private Direction direction;
	private Map<Direction, Direction> nextDirections = new HashMap<Direction, Direction>();

	public CounterClockWiseDirection() {
		direction = Direction.RIGHT;
		nextDirections.put(Direction.RIGHT, Direction.UP);
		nextDirections.put(Direction.UP, Direction.LEFT);
		nextDirections.put(Direction.LEFT, Direction.DOWN);
		nextDirections.put(Direction.DOWN, Direction.RIGHT);
	}

	public void turn() {
		direction = nextDirections.get(direction);
	}

	public Direction getDirection() {
		return direction;
	}
}
