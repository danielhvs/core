package br.com.danielhabib.unit.core;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import br.com.danielhabib.core.builder.LevelParser;
import br.com.danielhabib.core.component.LevelHandler;
import br.com.danielhabib.core.component.Position;

public class LevelHandlerTest {
	@Test
	public void nextLevel_1Level() throws Exception {
		LevelHandler handler = new LevelHandler();
		handler.setLevels(Arrays.asList("p:0,0"));

		List<LevelParser> parsers = handler.getParsers();

		assertEquals(new Position(0, 0), parsers.get(0).getPsico().getPosition());
	}

	@Test
	public void nextLevel_2Levels() throws Exception {
		LevelHandler handler = new LevelHandler();
		handler.setLevels(Arrays.asList("p:0,0", "p:1,0"));

		List<LevelParser> parsers = handler.getParsers();

		assertEquals(new Position(1, 0), parsers.get(1).getPsico().getPosition());
	}

}