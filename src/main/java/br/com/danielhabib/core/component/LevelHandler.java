package br.com.danielhabib.core.component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import br.com.danielhabib.core.builder.LevelParser;

public class LevelHandler {

	private List<String> levels;

	// For test only, for now
	public void setLevels(List<String> levels) {
		this.levels = levels;
	}

	public void setFiles(List<File> files) throws IOException {
		levels = new ArrayList<String>();
		for (File file : files) {
			levels.add(FileUtils.readFileToString(file));
		}
	}

	public List<LevelParser> getParsers() throws IOException {
		List<LevelParser> parsers = new ArrayList<LevelParser>();
		for (String level : levels) {
			parsers.add(new LevelParser(level));
		}
		return parsers;
	}

}
