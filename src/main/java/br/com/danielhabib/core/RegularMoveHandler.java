package br.com.danielhabib.core;

import java.util.HashMap;
import java.util.Map;

public class RegularMoveHandler implements IMoveHandler {

	private Position position;
	Map<Direction, Position> speedMap;
	private final MovingRules movingRules;

	public RegularMoveHandler(Position position, int speed, MovingRules movingRules) {
		this.position = position;
		this.movingRules = movingRules;
		initSpeedMap(speed);
	}

	public Position getPosition() {
		return position;
	}

	public void move(Direction direction) {
		Position nextPosition = position.add(speedMap.get(direction));
		if (movingRules.canMove(nextPosition)) {
			this.position = nextPosition;
		}
	}

	private void initSpeedMap(int speed) {
		Map<Direction, Position> map = new HashMap<Direction, Position>();
		map.put(Direction.UP, new Position(0, -speed));
		map.put(Direction.DOWN, new Position(0, speed));
		map.put(Direction.LEFT, new Position(-speed, 0));
		map.put(Direction.RIGHT, new Position(speed, 0));
		this.speedMap = map;
	}

	public void setSpeed(int speed) {
		initSpeedMap(speed);
	}
}
