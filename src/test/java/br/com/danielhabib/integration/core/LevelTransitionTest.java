package br.com.danielhabib.integration.core;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import br.com.danielhabib.core.builder.LevelParser;
import br.com.danielhabib.core.gui.Main2D;
import br.com.danielhabib.core.rules.IRulesObserver;

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
		psico.setMoveHandlerObserver(new IRulesObserver() {
			public void levelIsOver() throws InterruptedException {
				loadNextLevel();
			}

			private void loadNextLevel() throws InterruptedException {
				frame.dispose();

				buildFrame();
				parser = context.getBean("levelTransition2", LevelParser.class);
				psico = parser.getPsico();
				applet = new Main2D(psico, parser);
				psico.setMoveHandlerObserver(RULES_OBSERVER);
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
	protected List<LevelParser> parsers() {
		return Arrays.asList(context.getBean("levelTransition1", LevelParser.class));
	}

}
