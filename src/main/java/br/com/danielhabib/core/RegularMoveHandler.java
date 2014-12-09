package br.com.danielhabib.core;

import java.util.HashMap;
import java.util.Map;

public class RegularMoveHandler implements IMoveHandler {

	private Position position;
	Map<Direction, Position> speedMap;
	private final MovingRules movingRules;

	public RegularMoveHandler(Position position, MovingRules movingRules) {
		this.position = position;
		this.movingRules = movingRules;
		initSpeedMap();
	}

	public Position getPosition() {
		return position;
	}

	public boolean move(Direction direction) {
		Position nextPosition = position.add(speedMap.get(direction));
		if (movingRules.canMove(nextPosition)) {
			this.position = nextPosition;
			return true;
		}
		return false;
	}

	private void initSpeedMap() {
		Map<Direction, Position> map = new HashMap<Direction, Position>();
		map.put(Direction.UP, new Position(0, -Config.SIZE));
		map.put(Direction.DOWN, new Position(0, Config.SIZE));
		map.put(Direction.LEFT, new Position(-Config.SIZE, 0));
		map.put(Direction.RIGHT, new Position(Config.SIZE, 0));
		this.speedMap = map;
	}

	public boolean hasBall() {
		return movingRules.hasBall(position);
	}

	public PsicoComponent getBall() {
		return movingRules.getBall(position);
	}

	public PsicoComponent dropBall() {
		movingRules.dropBall(position);
		return new NullComponent();
	}
}
