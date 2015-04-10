package br.com.danielhabib.core.component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import br.com.danielhabib.core.builder.PsicoComponentBuilder;
import br.com.danielhabib.core.gui.Graphics;
import br.com.danielhabib.core.nulls.NullComponent;

public class Environment {

	private String input;
	private PsicoComponentBuilder builder;
	private List<PsicoComponent> walls;
	private List<PsicoComponent> balls;
	private List<PsicoComponent> goals;
	private Map<Position, PsicoComponentContainer> containers;
	private static final ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/config/beans.xml");

	public Environment() {
		this.builder = newPsicoComponentBuilder();
		this.input = "";
		initEnv();
	}

	private PsicoComponentBuilder newPsicoComponentBuilder() {
		return context.getBean("componentBuilder", PsicoComponentBuilder.class);
	}

	public Environment(String input) {
		this();
		this.input = input;
		initEnv();
	}

	public Environment(File envFile) throws IOException {
		this();
		this.input = readFile(envFile);
		initEnv();
	}

	private void initEnv() {
		this.walls = getComponentListForType('w');
		this.balls = getComponentListForType('o');
		this.goals = getComponentListForType('g');
		this.containers = new HashMap<Position, PsicoComponentContainer>();
		for (PsicoComponent ball : balls) {
			PsicoComponentContainer container = newContainer(ball);
			containers.put(ball.getPosition(), container);
		}
	}

	private PsicoComponentContainer newContainer(PsicoComponent ball) {
		return new PsicoComponentContainer(ball);
	}

	private String readFile(File envFile) throws IOException {
		return FileUtils.readFileToString(envFile);
	}

	public List<PsicoComponent> getComponentListForType(char type) {
		ArrayList<PsicoComponent> list = new ArrayList<PsicoComponent>();
		if (!StringUtils.isBlank(input)) {
			int y = 0;
			String[] lines = input.split("\n");
			for (String line : lines) {
				list.addAll(getComponentListForY(y, line, type));
				y++;
			}
		}
		return list;
	}

	private List<PsicoComponent> getComponentListForY(int y, String line, char type) {
		ArrayList<PsicoComponent> list = new ArrayList<PsicoComponent>();
		int x = 0;
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == type) {
				list.add(builder.build(type, new Position(x, y)));
			}
			x++;
		}
		return list;
	}

	public void addBall(Position position, PsicoComponent ball) {
		if (containers.containsKey(position)) {
			containers.get(position).push(ball);
		} else {
			containers.put(position, newContainer(ball));
		}
	}

	public void createBall(Position position) {
		PsicoComponent newBall = builder.build('o', position);
		addBall(position, newBall);
	}

	public boolean hasWall(Position nextPosition) {
		return hasComponentAt(nextPosition, walls);
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
		List<PsicoComponent> components = new ArrayList<PsicoComponent>(walls);
		components.addAll(goals);
		for (PsicoComponent component : components) {
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

	public void setWalls(List<PsicoComponent> walls) {
		this.walls = walls;
	}

	public void setGoals(List<PsicoComponent> goals) {
		this.goals = goals;
	}

}
