package br.com.danielhabib.core;

import java.util.HashMap;
import java.util.Map;

public class RegularMoveHandler implements IMoveHandler {

	private Position position;
	Map<Direction, Position> speedMap;

	public RegularMoveHandler(Position position, int speed) {
		this.position = position;
		initSpeedMap(speed);
	}

	public Position getPosition() {
		return position;
	}

	public void move(Direction direction) {
		position.add(speedMap.get(direction));
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
