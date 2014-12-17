package br.com.danielhabib.core;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class Environment extends PsicoComponent {

	private String input;
	private PsicoComponentBuilder builder;
	private List<PsicoComponent> walls;
	private List<PsicoComponent> balls;
	private Map<Position, PsicoComponentContainer> containers;
	private static final int NUMBER_X_OFFSET = -3;
	private static final int NUMBER_Y_OFFSET = 5;

	public Environment() {
		super(new Position(0, 0));
		this.builder = new PsicoComponentBuilder();
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
		this.containers = new HashMap<Position, PsicoComponentContainer>();
		for (PsicoComponent ball : balls) {
			PsicoComponentContainer container = newContainer(ball);
			containers.put(ball.getPosition(), container);
		}
	}

	private PsicoComponentContainer newContainer(PsicoComponent ball) {
		PsicoComponentContainer container = new PsicoComponentContainer(ball);
		container.setColorBuilder(new ColorBuilder(new Color[] { Color.BLUE, Color.RED, Color.BLACK, Color.GREEN, Color.MAGENTA }));
		return container;
	}

	private String readFile(File envFile) throws IOException {
		return FileUtils.readFileToString(envFile);
	}

	public List<PsicoComponent> getWalls() {
		return this.walls;
	}

	public List<PsicoComponent> getBalls() {
		return this.balls;
	}

	public List<PsicoComponent> getComponentListForType(char type) {
		ArrayList<PsicoComponent> list = new ArrayList<PsicoComponent>();
		if (!StringUtils.isBlank(input)) {
			int y = 0;
			String[] lines = input.split("\n");
			for (String line : lines) {
				list.addAll(getComponentListForY(y, line, type));
				y += Config.SIZE;
			}
		}
		return list;
	}

	private List<PsicoComponent> getComponentListForY(int y, String line, char type) {
		ArrayList<PsicoComponent> list = new ArrayList<PsicoComponent>();
		int x = 0;
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == type) {
				list.add(builder.build(type, x, y));
			}
			x += Config.SIZE;
		}
		return list;
	}

	public void addBall(Position position) {
		if (containers.containsKey(position)) {
			containers.get(position).push(new Ball(position));
		} else {
			containers.put(position, newContainer(new Ball(position)));
		}
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

	@Override
	void draw(Graphics g) {
		List<PsicoComponent> components = new ArrayList<PsicoComponent>(walls);
		for (PsicoComponent component : components) {
			component.draw(g);
		}

		for (Entry<Position, PsicoComponentContainer> entry : containers.entrySet()) {
			PsicoComponentContainer container = entry.getValue();
			container.draw(g);
		}

		g.setColor(Color.YELLOW);
		for (Entry<Position, PsicoComponentContainer> entry : containers.entrySet()) {
			PsicoComponentContainer container = entry.getValue();
			Position position = container.getPosition();
			int count = container.size();
			int x = position.getX() + Ball.OFFSET + xOffset(count) + Ball.DIAMETER / 2;
			int y = position.getY() + Ball.OFFSET + NUMBER_Y_OFFSET + Ball.DIAMETER / 2;
			g.drawString(String.valueOf(count), x, y);
		}
	}

	private int xOffset(int count) {
		int numDigits = String.valueOf(count).length();
		return NUMBER_X_OFFSET - 4 * (numDigits - 1);
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

}
