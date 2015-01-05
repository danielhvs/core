package br.com.danielhabib.integration.core;

import static org.junit.Assert.assertEquals;

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
	protected int setTimeoutMillis() {
		return 50;
	}

	@Override
	protected void setup() {
		rulesObserver = new TestObserver();
		parser.setMoveHandlerObserver(rulesObserver);
	}

	@Override
	protected String level() {
		return "w:0,0\nr:2,0-3,0\nw:4,0\np:1,0";
	}

}
