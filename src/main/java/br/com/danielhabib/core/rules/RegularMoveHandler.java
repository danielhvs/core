package br.com.danielhabib.core.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.danielhabib.core.Config;
import br.com.danielhabib.core.component.Environment;
import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.PsicoComponent;
import br.com.danielhabib.core.nulls.NullComponent;
import br.com.danielhabib.core.nulls.NullObserver;

public class RegularMoveHandler implements IMoveHandler {

	private Position position;
	Map<Direction, Position> speedMap;
	private Environment env;
	private List<GoalRule> rules;
	private IRulesObserver rulesObserver;

	public RegularMoveHandler(Position position, Environment env) {
		this.position = position;
		this.env = env;
		this.rulesObserver = new NullObserver();
		this.rules = new ArrayList<GoalRule>();
		initSpeedMap();
	}

	public RegularMoveHandler(Position position) {
		this(position, null);
	}

	public Position getPosition() {
		return position;
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

	public PsicoComponent dropBall(PsicoComponent ball) {
		env.addBall(position, ball);
		notifyIfLevelIsOver();
		return new NullComponent();
	}

	private void notifyIfLevelIsOver() {
		for (GoalRule rule : rules) {
			if (!rule.isLevelOver()) {
				return;
			}
		}
		try {
			rulesObserver.levelIsOver();
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public void setRules(List<GoalRule> rules) {
		this.rules = rules;
	}

	public void setObserver(IRulesObserver rulesObserver) {
		this.rulesObserver = rulesObserver;
	}

	public void setEnv(Environment env) {
		this.env = env;
	}

}
