package br.com.danielhabib.core.builder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import br.com.danielhabib.core.component.Component;
import br.com.danielhabib.core.component.ComponentContainer;
import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.Psico;
import br.com.danielhabib.core.gui.Graphics;
import br.com.danielhabib.core.nulls.NullComponent;
import br.com.danielhabib.core.rules.DirectionHandler;
import br.com.danielhabib.core.rules.GoalRule;
import br.com.danielhabib.core.rules.GrabbingRules;
import br.com.danielhabib.core.rules.IRulesObserver;
import br.com.danielhabib.core.rules.ImageHandler;
import br.com.danielhabib.core.rules.MovingRules;

public class LevelParser implements ApplicationContextAware {

	private ComponentBuilder componentBuilder;
	private String string;
	private Map<Character, List<Component>> map;
	private List<GoalRule> goalRules = new ArrayList<GoalRule>();
	private Psico psico;
	private Map<Position, ComponentContainer> containers = new HashMap<Position, ComponentContainer>();
	private ApplicationContext context;

	private MovingRules movingRules;
	private ImageHandler imageHandler;
	private DirectionHandler directionRules;
	private GrabbingRules grabbingRules;
	private File file;

	private void setupMoveHandler() {
		grabbingRules.setLevelParser(this);
		grabbingRules.setGoalRules(goalRules);
		movingRules.setLevelParser(this);
	}

	public List<GoalRule> getGoalRules() {
		return goalRules;
	}

	public List<Component> getWalls() {
		return nullSafe(map.get('w'));
	}

	public List<Component> getBalls() {
		return nullSafe(map.get('o'));
	}

	public List<Component> getGoals() {
		return nullSafe(map.get('g'));
	}

	private List<Component> nullSafe(List<Component> components) {
		return components == null ? new ArrayList<Component>() : components;
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
		Component newComponent = componentBuilder.build(type, position);
		add(type, newComponent);
	}

	// TODO: configure it via beans.xml using scope = prototype or setting the
	// position here
	private void buildPsico(String position) {
		String[] xy = position.split(",");
		int x = Integer.parseInt(xy[0]);
		int y = Integer.parseInt(xy[1]);
		this.psico = new Psico(directionRules, grabbingRules, imageHandler, new Position(x, y));
		this.psico.setSpeedMap(context.getBean("speedMap", Map.class));
		this.psico.setMovingRules(movingRules);
	}

	private void add(char type, Component component) {
		List<Component> list = map.get(type);
		list.add(component);
		map.put(type, list);
	}

	private void buildRule(String elements) {
		String[] positions = elements.split("-");
		Component ball = componentBuilder.build('o', new Position(positions[0]));
		add('o', ball);
		Position candidateGoalPosition = new Position(positions[1]);
		Component goal = componentBuilder.build('g', candidateGoalPosition);
		if (canAddGoal(candidateGoalPosition)) {
			add('g', goal);
		}
		goalRules.add(new GoalRule(ball, goal));
	}

	private boolean canAddGoal(Position candidateGoalPosition) {
		List<Component> goals = getGoals();
		boolean canAdd = true;
		for (Component goal : goals) {
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
		grabbingRules.setObserver(iRulesObserver);
	}

	public void build() throws IOException {
		this.string = FileUtils.readFileToString(file);
		map = new HashMap<Character, List<Component>>();
		map.put('w', new ArrayList<Component>());
		map.put('o', new ArrayList<Component>());
		map.put('g', new ArrayList<Component>());
		parse();
		addBallsInContainers();
		setupMoveHandler();
	}

	public void addBall(Position position, Component ball) {
		if (containers.containsKey(position)) {
			containers.get(position).push(ball);
		} else {
			containers.put(position, newContainer(ball));
		}
	}

	private ComponentContainer newContainer(Component ball) {
		return new ComponentContainer(ball);
	}

	public void createBall(Position position) {
		Component newBall = componentBuilder.build('o', position);
		addBall(position, newBall);
	}

	public boolean hasWall(Position nextPosition) {
		return hasComponentAt(nextPosition, getWalls());
	}

	public boolean hasComponentAt(Position position, List<Component> components) {
		for (Component component : components) {
			if (component.getPosition().equals(position)) {
				return true;
			}
		}
		return false;
	}

	public void draw(Graphics g) {
		for (Component component : getWalls()) {
			component.draw(g);
		}
		for (Component component : getGoals()) {
			component.draw(g);
		}

		for (Entry<Position, ComponentContainer> entry : containers.entrySet()) {
			ComponentContainer container = entry.getValue();
			container.draw(g);
		}
	}

	public Component popBallAt(Position position) {
		Component component;
		if (containers.containsKey(position)) {
			ComponentContainer container = containers.get(position);
			component = container.pop();
			if (container.isEmpty()) {
				containers.remove(position);
			}
		} else {
			component = new NullComponent();
		}
		return component;
	}

	public void addBallsInContainers() {
		for (Component ball : getBalls()) {
			addBall(ball.getPosition(), ball);
		}
	}

	public void setComponentBuilder(ComponentBuilder componentBuilder) {
		this.componentBuilder = componentBuilder;
	}

	public void setMovingRules(MovingRules movingRules) {
		this.movingRules = movingRules;
	}

	public void setImageHandler(ImageHandler imageHandler) {
		this.imageHandler = imageHandler;
	}

	public void setDirectionRules(DirectionHandler directionRules) {
		this.directionRules = directionRules;
	}

	public void setGrabbingRules(GrabbingRules grabbingRules) {
		this.grabbingRules = grabbingRules;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

}
