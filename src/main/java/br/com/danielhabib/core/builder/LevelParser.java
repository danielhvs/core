package br.com.danielhabib.core.builder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

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
	private final String string;
	private Map<Character, List<PsicoComponent>> map = new HashMap<Character, List<PsicoComponent>>();
	private List<GoalRule> goalRules = new ArrayList<GoalRule>();
	private Environment env;
	private RegularMoveHandler moveHandler = new NullMoveHandler();
	private Psico psico;
	private static final ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/config/beans.xml");

	public LevelParser(File file) throws IOException {
		this.string = FileUtils.readFileToString(file);
		doParse();
	}

	public LevelParser(String string) {
		this.string = string;
		doParse();
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

	private PsicoComponentBuilder newPsicoComponentBuilder() {
		PsicoComponentBuilder builder = new PsicoComponentBuilder();
		// TODO: implements ApplicationContextAware
		builder.registerTypeBuilder('w', context.getBean("wallBuilder", ATypeBuilder.class));
		builder.registerTypeBuilder('o', context.getBean("ballBuilder", ATypeBuilder.class));
		builder.registerTypeBuilder('g', context.getBean("goalBuilder", ATypeBuilder.class));
		return builder;
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
		String[] components = string.split("\n");
		for (String component : components) {
			String[] data = component.split(":");
			String stype = data[0];
			if (stype.isEmpty()) {
				continue;
			}
			char type = stype.charAt(0);
			if (type == 'p') {
				buildPsico(data[1]);
			} else if (type == 'r') {
				buildRule(data[1]);
			} else {
				String[] points = data[1].split("-");
				if (points.length == 1) {
					Position position = positionFromCSV(data[1]);
					addComponent(type, position);
				} else {
					String[] pi = points[0].split(",");
					String[] pf = points[1].split(",");
					if (pi[1].equals(pf[1])) {
						int y = extracted(pi);
						int finalX = Config.SIZE * Integer.parseInt(pf[0]);
						for (int x = Config.SIZE * Integer.parseInt(pi[0]); x <= finalX; x += Config.SIZE) {
							addComponent(type, new Position(x, y));
						}
					} else if (pi[0].equals(pf[0])) {
						int x = Config.SIZE * Integer.parseInt(pi[0]);
						int finalY = extracted(pf);
						for (int y = extracted(pi); y <= finalY; y += Config.SIZE) {
							addComponent(type, new Position(x, y));
						}
					}
				}
			}
		}
	}

	private int extracted(String[] pf) {
		return Config.SIZE * Integer.parseInt(pf[1]);
	}

	private Position positionFromCSV(String data) {
		String[] positions = data.split(",");
		int x = Config.SIZE * Integer.parseInt(positions[0]);
		int y = extracted(positions);
		return new Position(x, y);
	}

	private void addComponent(char type, Position position) {
		PsicoComponent newComponent = builder.build(type, position.getX(), position.getY());
		add(type, newComponent);
	}

	private void buildPsico(String position) {
		String[] xy = position.split(",");
		int x = Config.SIZE * Integer.parseInt(xy[0]);
		int y = extracted(xy);
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

	private void buildRule(String elements) {
		String[] positions = elements.split("-");
		String[] ballPosition = positions[0].split(",");
		String[] goalPosition = positions[1].split(",");
		int xb = Config.SIZE * Integer.parseInt(ballPosition[0]);
		int yb = extracted(ballPosition);
		int xg = Config.SIZE * Integer.parseInt(goalPosition[0]);
		int yg = extracted(goalPosition);
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

	private void doParse() {
		parseIt();
		this.env = initEnv();
		setupMoveHandler();
	}


}
