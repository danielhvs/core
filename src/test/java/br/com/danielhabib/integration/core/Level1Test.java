package br.com.danielhabib.integration.core;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import br.com.danielhabib.core.builder.LevelParser;

public class Level1Test extends AbstractIntegrationTest {

	private TestObserver rulesObserver;

	@Override
	protected void testIt() throws Exception {
		left();
		left();
		left();
		move();
		grab();
		left();
		move();
		drop();
		assertEquals(false, rulesObserver.isOver());

		grab();
		move();
		drop();
		move();
		move();

		assertEquals(true, rulesObserver.isOver());
		rulesObserver.setNotOver();

		grab();
		left();
		drop();

		assertEquals(false, rulesObserver.isOver());

		left();
		left();
	}

	@Override
	protected void setup() {
		rulesObserver = new TestObserver();
		psico.setMoveHandlerObserver(rulesObserver);
	}

	@Override
	protected List<LevelParser> parsers() {
		return Arrays.asList(
				context.getBean("level1_1Parser", LevelParser.class),
				context.getBean("level1_2Parser", LevelParser.class),
				context.getBean("level1_3Parser", LevelParser.class));
	}

}
