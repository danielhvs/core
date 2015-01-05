package br.com.danielhabib.core;

import java.util.ArrayList;
import java.util.List;

import br.com.danielhabib.core.builder.LevelParser;

public class LevelHandler {

	private final String[] levels;
	private List<LevelParser> parsers = new ArrayList<LevelParser>();

	public LevelHandler(String[] levels) {
		this.levels = levels;
		buildParsers();
	}

	private void buildParsers() {
		for (String level : levels) {
			LevelParser parser = new LevelParser(level);
			parsers.add(parser);
		}
	}

	public List<LevelParser> getParsers() {
		return parsers;
	}

}
