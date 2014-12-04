package br.com.danielhabib.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Environment {

	public static final int WALL_SIZE = 64;
	private String input;

	public Environment(String input) {
		this.input = input;
	}

	public List<Wall> getWalls() {
		ArrayList<Wall> list = new ArrayList<Wall>();
		if (!StringUtils.isBlank(input)) {
			int y = 0;
			String[] lines = input.split("\n");
			for (String line : lines) {
				list.addAll(getWallsListForY(y, line));
				y += WALL_SIZE;
			}
		}
		return list;
	}

	private List<Wall> getWallsListForY(int y, String line) {
		ArrayList<Wall> list = new ArrayList<Wall>();
		int x = 0;
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == 'w') {
				list.add(new Wall(new Position(x, y), WALL_SIZE));
			}
			x += WALL_SIZE;
		}
		return list;
	}
}
