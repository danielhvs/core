package br.com.danielhabib.integration.core;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import br.com.danielhabib.core.builder.LevelParser;

public class AcceptanceTest extends AbstractIntegrationTest {

	private TestObserver rulesObserver;

	@Override
	protected void testIt() throws Exception {
		assertEquals(false, rulesObserver.isOver());
		switch (level) {
		case 1:
			testLevel1();
			break;
		case 2:
			testLevel2();
			break;
		}
	}

	private void testLevel2() throws InterruptedException {
		up();
		grab();
		down();
		move();
		down();
		drop();
		up();
		move();
		assertEquals(true, rulesObserver.isOver());
	}

	private void testLevel1() throws InterruptedException {
		move();
		grab();
		move();
		up();
		move();
		move();
		drop();
		assertEquals(true, rulesObserver.isOver());
	}


	@Override
	protected void setup() {
		rulesObserver = new TestObserver();
		parser.setMoveHandlerObserver(rulesObserver);
	}

	@Override
	protected int xWindowSize() {
		return CONFIG_SIZE * 7;
	}

	@Override
	protected int yWindowSize() {
		return CONFIG_SIZE * 6;
	}

	@Override
	protected List<LevelParser> parsers() {
		return Arrays.asList(context.getBean("acceptance1Parser", LevelParser.class), context.getBean("acceptance2Parser", LevelParser.class));
	}

}
