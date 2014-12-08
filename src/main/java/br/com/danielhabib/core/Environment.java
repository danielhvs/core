package br.com.danielhabib.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class Environment {

	private String input;
	private PsicoComponentBuilder builder;

	public Environment() {
		this.builder = new PsicoComponentBuilder();
	}

	public Environment(String input) {
		this();
		this.input = input;
	}

	public Environment(File envFile) throws IOException {
		this();
		this.input = readFile(envFile);
	}

	private String readFile(File envFile) throws IOException {
		return FileUtils.readFileToString(envFile);
	}

	public List<PsicoComponent> getWalls() {
		return getComponentListForType('w');
	}

	public List<PsicoComponent> getBalls() {
		return getComponentListForType('o');
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

}
