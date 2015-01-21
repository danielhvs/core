package br.com.danielhabib.integration.core;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import br.com.danielhabib.core.Config;

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
	protected List<String> levels() {
		return Arrays.asList(
				 "w:0,0\nw:1,0\nw:2,0\nw:3,0\nw:4,0\nw:5,0\nw:6,0\n"
			   + "w:0,1\nw:6,1\n"
			   + "w:0,2\nw:6,2\n"
			   + "w:0,3\nw:4,3\nw:5,3\nw:6,3\np:1,3\nr:2,3-5,2\n"
			   + "w:0,4\nw:1,4\nw:2,4\nw:3,4\nw:4,4\nw:5,4\nw:6,4\n",
			   
				 "w:0,0\nw:1,0\nw:2,0\nw:3,0\nw:4,0\nw:5,0\nw:6,0\n"
			   + "w:0,1\nw:6,1\n"
			   + "w:0,2\nw:6,2\nr:1,2-2,4\n"
			   + "w:0,3\nw:6,3\np:1,3\n"
			   + "w:0,4\nw:6,4\nw:1,4\nw:3,4\nw:4,4\nw:5,4\nw:6,4\n"
		       + "w:0,5\nw:1,5\nw:2,5\nw:3,5\nw:4,5\nw:5,5\nw:6,5\n");
	}
	
	@Override
	protected void setup() {
		rulesObserver = new TestObserver();
		parser.setMoveHandlerObserver(rulesObserver);
	}

	@Override
	protected int timeoutMillis() {
		return level == 1 ? 100 : 50;
	}

	@Override
	protected int xWindowSize() {
		return Config.SIZE * 7;
	}

	@Override
	protected int yWindowSize() {
		return Config.SIZE * 6;
	}

}
