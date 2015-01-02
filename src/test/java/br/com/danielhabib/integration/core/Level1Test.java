package br.com.danielhabib.integration.core;

import static org.junit.Assert.assertEquals;
import br.com.danielhabib.core.Config;
import br.com.danielhabib.core.Position;

public class Level1Test extends AbstractIntegrationTest {

	private TestObserver rulesObserver;

	@Override
	protected void testIt() throws Exception {
		move();
		grab();
		drop();
		assertEquals(false, rulesObserver.isOver());

		grab();
		move();
		drop();

		assertEquals(true, rulesObserver.isOver());
		rulesObserver.setNotOver();

		grab();
		left();
		drop();

		assertEquals(false, rulesObserver.isOver());
	}

	@Override
	protected Position psicoInitialPosition() {
		return new Position(Config.SIZE, 0);
	}

	@Override
	protected int setTimeoutMillis() {
		return 25;
	}

	@Override
	protected void setup() {
		rulesObserver = new TestObserver();
		moveHandler.setObserver(rulesObserver);
	}

	@Override
	protected String level() {
		return "w:0,0\nr:2,0-3,0\nw:4,0";
	}

}
