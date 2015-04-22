package br.com.danielhabib.core.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.Psico;
import br.com.danielhabib.core.component.PsicoComponent;
import br.com.danielhabib.core.component.PsicoComponentContainer;
import br.com.danielhabib.core.gui.Graphics;
import br.com.danielhabib.core.nulls.NullComponent;
import br.com.danielhabib.core.nulls.NullMoveHandler;
import br.com.danielhabib.core.rules.DirectionHandler;
import br.com.danielhabib.core.rules.GoalRule;
import br.com.danielhabib.core.rules.IRulesObserver;
import br.com.danielhabib.core.rules.ImageHandler;
import br.com.danielhabib.core.rules.RegularMoveHandler;

public class LevelParser {

	private PsicoComponentBuilder builder;
	private final String string;
	private Map<Character, List<PsicoComponent>> map;
	private List<GoalRule> goalRules = new ArrayList<GoalRule>();
	private RegularMoveHandler moveHandler = new NullMoveHandler();
	private Psico psico;
	private Map<Position, PsicoComponentContainer> containers = new HashMap<Position, PsicoComponentContainer>();
	private static final ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/config/beans.xml");

	// FIXME: Single Responsibility: parse, build, draw, manage containers...
	public LevelParser(String string) {
		this.string = string;
		doParse();
	}

	private void setupMoveHandler() {
		moveHandler.setEnv(this);
		moveHandler.setRules(goalRules);
	}

	private void parseIt() {
		this.builder = context.getBean("componentBuilder", PsicoComponentBuilder.class);
		map = new HashMap<Character, List<PsicoComponent>>();
		map.put('w', new ArrayList<PsicoComponent>());
		map.put('o', new ArrayList<PsicoComponent>());
		map.put('g', new ArrayList<PsicoComponent>());
		parse();
		setBalls(getBalls());
	}

	public List<GoalRule> getGoalRules() {
		return goalRules;
	}

	public List<PsicoComponent> getWalls() {
		return nullSafe(map.get('w'));
	}

	public List<PsicoComponent> getBalls() {
		return nullSafe(map.get('o'));
	}

	public List<PsicoComponent> getGoals() {
		return nullSafe(map.get('g'));
	}

	private List<PsicoComponent> nullSafe(List<PsicoComponent> components) {
		return components == null ? new ArrayList<PsicoComponent>() : components;
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
					addComponent(type, new Position(data[1]));
				} else {
					String[] pi = points[0].split(",");
					String[] pf = points[1].split(",");
					if (pi[1].equals(pf[1])) {
						int y = Integer.parseInt(pi[1]);
						int finalX = Integer.parseInt(pf[0]);
						for (int x = Integer.parseInt(pi[0]); x <= finalX; x++) {
							addComponent(type, new Position(x, y));
						}
					} else if (pi[0].equals(pf[0])) {
						int x = Integer.parseInt(pi[0]);
						int finalY = Integer.parseInt(pf[1]);
						for (int y = Integer.parseInt(pi[1]); y <= finalY; y++) {
							addComponent(type, new Position(x, y));
						}
					}
				}
			}
		}
	}

	private void addComponent(char type, Position position) {
		PsicoComponent newComponent = builder.build(type, position);
		add(type, newComponent);
	}

	private void buildPsico(String position) {
		String[] xy = position.split(",");
		int x = Integer.parseInt(xy[0]);
		int y = Integer.parseInt(xy[1]);
		moveHandler = context.getBean("moveHandler", RegularMoveHandler.class);
		moveHandler.setPosition(new Position(x, y));
		DirectionHandler handler = context.getBean("directionHandler", DirectionHandler.class);
		ImageHandler imageHandler = context.getBean("imageHandler", ImageHandler.class);
		this.psico = new Psico(handler, moveHandler, imageHandler);
	}

	private void add(char type, PsicoComponent component) {
		List<PsicoComponent> list = map.get(type);
		list.add(component);
		map.put(type, list);
	}

	private void buildRule(String elements) {
		String[] positions = elements.split("-");
		PsicoComponent ball = builder.build('o', new Position(positions[0]));
		add('o', ball);
		Position candidateGoalPosition = new Position(positions[1]);
		PsicoComponent goal = builder.build('g', candidateGoalPosition);
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

	public void setMoveHandlerObserver(IRulesObserver iRulesObserver) {
		moveHandler.setObserver(iRulesObserver);
	}

	private void doParse() {
		parseIt();
		setupMoveHandler();
	}

	public void addBall(Position position, PsicoComponent ball) {
		if (containers.containsKey(position)) {
			containers.get(position).push(ball);
		} else {
			containers.put(position, newContainer(ball));
		}
	}

	private PsicoComponentContainer newContainer(PsicoComponent ball) {
		return new PsicoComponentContainer(ball);
	}

	public void createBall(Position position) {
		PsicoComponent newBall = builder.build('o', position);
		addBall(position, newBall);
	}

	public boolean hasWall(Position nextPosition) {
		return hasComponentAt(nextPosition, getWalls());
	}

	public boolean hasComponentAt(Position position, List<PsicoComponent> components) {
		for (PsicoComponent component : components) {
			if (component.getPosition().equals(position)) {
				return true;
			}
		}
		return false;
	}

	public void draw(Graphics g) {
		for (PsicoComponent component : getWalls()) {
			component.draw(g);
		}
		for (PsicoComponent component : getGoals()) {
			component.draw(g);
		}

		for (Entry<Position, PsicoComponentContainer> entry : containers.entrySet()) {
			PsicoComponentContainer container = entry.getValue();
			container.draw(g);
		}
	}

	public PsicoComponent popBallAt(Position position) {
		PsicoComponent component;
		if (containers.containsKey(position)) {
			PsicoComponentContainer container = containers.get(position);
			component = container.pop();
			if (container.isEmpty()) {
				containers.remove(position);
			}
		} else {
			component = new NullComponent();
		}
		return component;
	}

	public void setBalls(List<PsicoComponent> balls) {
		for (PsicoComponent ball : balls) {
			addBall(ball.getPosition(), ball);
		}
	}

}
