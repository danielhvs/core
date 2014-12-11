package br.com.danielhabib.core;

import java.util.HashMap;
import java.util.Map;

public class RegularMoveHandler implements IMoveHandler {

	private Position position;
	Map<Direction, Position> speedMap;
	private Environment env;

	public RegularMoveHandler(Position position, Environment env) {
		this.position = position;
		this.env = env;
		initSpeedMap();
	}

	public Position getPosition() {
		return position;
	}

	public boolean hasBall() {
		return env.hasBall(position);
	}

	public boolean move(Direction direction) {
		Position nextPosition = position.add(speedMap.get(direction));
		if (canMove(nextPosition)) {
			this.position = nextPosition;
			return true;
		}
		return false;
	}

	public PsicoComponent getBall() {
		return env.popBallAt(position);
	}

	public PsicoComponent dropBall() {
		env.addBall(position);
		return new NullComponent();
	}

	private boolean canMove(Position nextPosition) {
		return !env.hasWall(nextPosition);
	}

	private void initSpeedMap() {
		Map<Direction, Position> map = new HashMap<Direction, Position>();
		map.put(Direction.UP, new Position(0, -Config.SIZE));
		map.put(Direction.DOWN, new Position(0, Config.SIZE));
		map.put(Direction.LEFT, new Position(-Config.SIZE, 0));
		map.put(Direction.RIGHT, new Position(Config.SIZE, 0));
		this.speedMap = map;
	}

}
