package br.com.danielhabib.core.component;

import java.util.ArrayList;
import java.util.List;

import br.com.danielhabib.core.builder.LevelParser;

public class LevelHandler {

	private List<LevelParser> parsers = new ArrayList<LevelParser>();
	private final List<String> levels;

	public LevelHandler(List<String> levels) {
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
