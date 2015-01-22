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
import br.com.danielhabib.core.component.Environment;
import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.Psico;
import br.com.danielhabib.core.component.PsicoComponent;
import br.com.danielhabib.core.nulls.NullMoveHandler;
import br.com.danielhabib.core.rules.CounterClockWiseDirection;
import br.com.danielhabib.core.rules.GoalRule;
import br.com.danielhabib.core.rules.IRulesObserver;
import br.com.danielhabib.core.rules.ImageHandler;
import br.com.danielhabib.core.rules.RegularMoveHandler;

public class LevelParser {

	private PsicoComponentBuilder builder;
	private static final Color[] WALL_COLORS = new Color[] { Color.BLACK, Color.DARK_GRAY.darker(), Color.DARK_GRAY, Color.DARK_GRAY.brighter() };
	private static final Color[] BALL_COLORS = new Color[] { Color.BLUE, Color.RED, Color.ORANGE, Color.CYAN, Color.GREEN, Color.YELLOW };
	private static final Color[] GOAL_COLORS = new Color[] { Color.ORANGE.darker() };
	private final String string;
	private Map<Character, List<PsicoComponent>> map = new HashMap<Character, List<PsicoComponent>>();
	private List<GoalRule> goalRules = new ArrayList<GoalRule>();
	private Environment env;
	private RegularMoveHandler moveHandler = new NullMoveHandler();
	private Psico psico;

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
		this.env = initEnv();
		setupMoveHandler();
	}

	public LevelParser(String string) {
		this.string = string;
		parseIt();
		this.env = initEnv();
		setupMoveHandler();
	}

	private void setupMoveHandler() {
		moveHandler.setEnv(env);
		moveHandler.setRules(getGoalRules());
	}

	private Environment initEnv() {
		Environment env = new Environment();
		env.setBalls(getBalls());
		env.setWalls(getWalls());
		env.setGoals(getGoals());
		return env;
	}

	private void parseIt() {
		this.builder = newPsicoComponentBuilder();
		map.put('w', new ArrayList<PsicoComponent>());
		map.put('o', new ArrayList<PsicoComponent>());
		map.put('g', new ArrayList<PsicoComponent>());
		parse();
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
		if (string.isEmpty()) {
			return;
		}
		String[] tokens = string.split("\n");
		for (String token : tokens) {
			String[] elements = token.split(":");
			String stype = elements[0];
			char type = stype.charAt(0);
			if (type == 'p') {
				buildPsico(elements[1]);
			} else if (type == 'r') {
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

	private void buildPsico(String position) {
		String[] xy = position.split(",");
		int x = Config.SIZE * Integer.parseInt(xy[0]);
		int y = Config.SIZE * Integer.parseInt(xy[1]);
		moveHandler = new RegularMoveHandler(new Position(x, y));

		this.psico = new Psico(new CounterClockWiseDirection(), moveHandler, new ImageHandler());
	}

	public Environment getEnv() {
		return env;
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
		PsicoComponent ball = builder.build('o', xb, yb);
		add('o', ball);
		Position candidateGoalPosition = new Position(xg, yg);
		PsicoComponent goal = builder.build('g', xg, yg);
		if (canAddGoal(candidateGoalPosition)) {
			add('g', goal);
		}
		goalRules.add(new GoalRule(ball, goal));
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

	public Psico getPsico() {
		return psico;
	}

	public RegularMoveHandler getMoveHandler() {
		return moveHandler;
	}

	public void setMoveHandlerObserver(IRulesObserver iRulesObserver) {
		moveHandler.setObserver(iRulesObserver);
	}

}
