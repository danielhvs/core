package br.com.danielhabib.integration.core;

import static org.junit.Assert.assertEquals;
import br.com.danielhabib.core.IRulesObserver;
import br.com.danielhabib.core.Main2D;
import br.com.danielhabib.core.builder.LevelParser;

public class LevelTransitionTest extends AbstractIntegrationTest {

	private static final TestObserver RULES_OBSERVER = new TestObserver();

	@Override
	protected void testIt() throws Exception {
		move();
		grab();
		move();
		drop();
	}

	@Override
	protected void setup() {
		parser.setMoveHandlerObserver(new IRulesObserver() {
			public void levelIsOver() throws InterruptedException {
				loadNextLevel();
			}

			private void loadNextLevel() throws InterruptedException {
				frame.dispose();

				buildFrame();
				parser = new LevelParser("w:0,0\nr:2,0-3,0\nw:4,0\nr:2,0-3,0\np:1,0");
				psico = parser.getPsico();
				applet = new Main2D(psico, parser.getEnv());
				parser.setMoveHandlerObserver(RULES_OBSERVER);
				setupFrame();
				setupCommands();

				sleep();
				testLevel2();
			}

			private void testLevel2() throws InterruptedException {
				move();
				grab();
				move();
				drop();
				assertEquals(false, RULES_OBSERVER.isOver());

				left();
				grab();
				move();
				drop();
				assertEquals(true, RULES_OBSERVER.isOver());
			}
		});
	}

	@Override
	protected String level() {
		return "w:0,0\nr:2,0-3,0\nw:4,0\np:1,0";
	}

	@Override
	protected int setTimeoutMillis() {
		return 75;
	}

}