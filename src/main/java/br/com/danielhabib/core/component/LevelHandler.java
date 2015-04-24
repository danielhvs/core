package br.com.danielhabib.core.component;

import java.util.List;

import br.com.danielhabib.core.builder.LevelParser;

public class LevelHandler {

	private List<LevelParser> parsers;

	public List<LevelParser> getParsers() {
		return parsers;
	}

	public void setParsers(List<LevelParser> parsers) {
		this.parsers = parsers;
	}

}
