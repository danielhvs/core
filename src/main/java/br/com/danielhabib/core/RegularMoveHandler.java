package br.com.danielhabib.core;

import java.util.HashMap;
import java.util.Map;

public class RegularMoveHandler implements IMoveHandler {

	private Position position;
	Map<Direction, Position> speedMap;

	public RegularMoveHandler(Position position) {
		this.position = position;
		this.speedMap = initSpeedMap();
	}

	public Position getPosition() {
		return position;
	}

	public void move(Direction direction) {
		position.add(speedMap.get(direction));
	}

	private Map<Direction, Position> initSpeedMap() {
		HashMap<Direction, Position> map = new HashMap<Direction, Position>();
		map.put(Direction.UP, new Position(0, 1));
		map.put(Direction.DOWN, new Position(0, -1));
		map.put(Direction.LEFT, new Position(-1, 0));
		map.put(Direction.RIGHT, new Position(1, 0));
		return map;
	}
}
