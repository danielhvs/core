package br.com.danielhabib.core.builder;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import br.com.danielhabib.core.Config;
import br.com.danielhabib.core.GoalRule;
import br.com.danielhabib.core.Position;
import br.com.danielhabib.core.PsicoComponent;

public class LevelParser {

	private PsicoComponentBuilder builder;
	private static final Color[] WALL_COLORS = new Color[] { Color.BLACK, Color.DARK_GRAY.darker(), Color.DARK_GRAY, Color.DARK_GRAY.brighter() };
	private static final Color[] BALL_COLORS = new Color[] { Color.BLUE, Color.RED, Color.ORANGE, Color.CYAN, Color.GREEN, Color.YELLOW };
	private static final Color[] GOAL_COLORS = new Color[] { Color.ORANGE.darker() };
	private final String string;
	private Map<Character, List<PsicoComponent>> map = new HashMap<Character, List<PsicoComponent>>();
	private List<GoalRule> goalRules = new ArrayList<GoalRule>();

	private PsicoComponentBuilder newPsicoComponentBuilder() {
		PsicoComponentBuilder builder = new PsicoComponentBuilder();
		builder.registerTypeBuilder('w', new WallBuilder(new ColorBuilder(WALL_COLORS)));
		builder.registerTypeBuilder('o', new BallBuilder(new ColorBuilder(BALL_COLORS)));
		builder.registerTypeBuilder('g', new GoalBuilder(new ColorBuilder(GOAL_COLORS)));
		return builder;
	}

	public LevelParser(File file) throws IOException {
		this.string = FileUtils.readFileToString(file);
		parseIt();
	}

	public LevelParser(String string) {
		this.string = string;
		parseIt();
	}

	private void parseIt() {
		this.builder = newPsicoComponentBuilder();
		map.put('w', new ArrayList<PsicoComponent>());
		map.put('o', new ArrayList<PsicoComponent>());
		map.put('g', new ArrayList<PsicoComponent>());
		parse();
		computeRules();
	}

	public List<GoalRule> getGoalRules() {
		return goalRules;
	}

	public List<PsicoComponent> getWalls() {
		return map.get('w');

	}

	public List<PsicoComponent> getBalls() {
		return map.get('o');
	}

	public List<PsicoComponent> getGoals() {
		return map.get('g');
	}

	private void parse() {
		String[] tokens = string.split("\n");
		for (String token : tokens) {
			String[] elements = token.split(":");
			String stype = elements[0];
			char type = stype.charAt(0);
			if (type == 'r') {
				parseRule(elements[1]);
			} else {
				String[] positions = elements[1].split(",");
				int x = Config.SIZE * Integer.parseInt(positions[0]);
				int y = Config.SIZE * Integer.parseInt(positions[1]);
				PsicoComponent component = builder.build(type, x, y);
				add(type, component);
			}
		}
	}

	private void add(char type, PsicoComponent component) {
		List<PsicoComponent> list = map.get(type);
		list.add(component);
		map.put(type, list);
	}

	private void parseRule(String elements) {
		String[] positions = elements.split("-");
		String[] ballPosition = positions[0].split(",");
		String[] goalPosition = positions[1].split(",");
		int xb = Config.SIZE * Integer.parseInt(ballPosition[0]);
		int yb = Config.SIZE * Integer.parseInt(ballPosition[1]);
		int xg = Config.SIZE * Integer.parseInt(goalPosition[0]);
		int yg = Config.SIZE * Integer.parseInt(goalPosition[1]);
		add('o', builder.build('o', xb, yb));
		Position candidateGoalPosition = new Position(xg, yg);
		if (canAddGoal(candidateGoalPosition)) {
			add('g', builder.build('g', xg, yg));
		}
	}

	private boolean canAddGoal(Position candidateGoalPosition) {
		List<PsicoComponent> goals = getGoals();
		boolean canAdd = true;
		for (PsicoComponent goal : goals) {
			if (candidateGoalPosition.equals(goal.getPosition())) {
				canAdd = false;
				break;
			}
		}
		return canAdd;
	}

	private void computeRules() {
		List<PsicoComponent> goals = getGoals();
		List<PsicoComponent> balls = getBalls();
		for (PsicoComponent goal : goals) {
			Position position = goal.getPosition();
			List<PsicoComponent> ballsAtPosition = new ArrayList<PsicoComponent>();
			for (PsicoComponent ball : balls) {
				if (ball.getPosition().equals(position)) {
					ballsAtPosition.add(ball);
				}
			}
			goalRules.add(new GoalRule(ballsAtPosition, goal));
		}
	}

}
