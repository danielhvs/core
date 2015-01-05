package br.com.danielhabib.core;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import br.com.danielhabib.core.builder.LevelParser;

public class LevelHandlerTest {
	@Test
	public void nextLevel_1Level() throws Exception {
		LevelHandler handler = new LevelHandler(new String[] { "p:0,0" });

		List<LevelParser> parsers = handler.getParsers();

		assertEquals(new Position(0, 0), parsers.get(0).getPsico().getPosition());
	}

	@Test
	public void nextLevel_2Levels() throws Exception {
		LevelHandler handler = new LevelHandler(new String[] { "p:0,0", "p:1,0" });

		List<LevelParser> parsers = handler.getParsers();

		assertEquals(new Position(Config.SIZE, 0), parsers.get(1).getPsico().getPosition());
	}
}
