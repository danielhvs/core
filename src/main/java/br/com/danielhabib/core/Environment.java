package br.com.danielhabib.core;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class Environment extends PsicoComponent {

	private String input;
	private PsicoComponentBuilder builder;
	private List<PsicoComponent> walls;
	private List<PsicoComponent> balls;

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

	public void removeBall(PsicoComponent ball) {
		balls.remove(ball);
	}

	public void addBall(Position position) {
		balls.add(new Ball(position));
	}

	public boolean hasBall(Position position) {
		return hasComponentAt(position, balls);
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

	public PsicoComponent getBallAt(Position position) {
		for (PsicoComponent ball : balls) {
			if (ball.getPosition().equals(position)) {
				return ball;
			}
		}
		return new NullComponent();
	}

	@Override
	void draw(Graphics g) {
		List<PsicoComponent> components = new ArrayList<PsicoComponent>(walls);
		components.addAll(balls);
		for (PsicoComponent component : components) {
			component.draw(g);
		}
	}

	public PsicoComponent popBallAt(Position position) {
		PsicoComponent ball = getBallAt(position);
		removeBall(ball);
		return ball;
	}

}
