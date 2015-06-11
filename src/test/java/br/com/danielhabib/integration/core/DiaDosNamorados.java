package br.com.danielhabib.integration.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.com.danielhabib.core.builder.LevelParser;
import br.com.danielhabib.core.component.Component;
import br.com.danielhabib.core.component.Position;

public class DiaDosNamorados extends AbstractIntegrationTest {

	private int initialX = 1;
	private int initialY = 17;


	@Override
	protected List<LevelParser> parsers() {
		return Arrays.asList(context.getBean("level1Parser", LevelParser.class));
	}

	@Override
	protected void testIt() throws Exception {
		setTimeout(8000);
		sleep();
		setTimeout(1000);
		List<Position> finalPositions = new ArrayList<Position>();

		List<Component> goals = parser.getGoals();
		for (Component goal : goals) {
			finalPositions.add(goal.getPosition());
		}
		Collections.shuffle(finalPositions);

		// From initial position
		for (Position position : finalPositions) {
			grabOneBall();
			gotoPosition(position);
			drop();
			backToInitialPosition();
		}
		comemora();
	}

	private void comemora() throws InterruptedException {
		setTimeout(300000);
		sleep();
	}

	private void gira() throws InterruptedException {
		int lastTimeout = getTimeout();
		setTimeout(50);
		for (int i = 0; i < 4 * 5; i++) {
			turn();
		}
		setTimeout(lastTimeout);
	}

	private void gotoPosition(Position position) throws InterruptedException {
		while (psico.getPosition().y() != position.y()) {
			up();
		}
		while (psico.getPosition().x() != position.x()) {
			move();
		}
	}

	private void backToInitialPosition() throws InterruptedException {
		backToInitialYPosition();
		backToInitialXPosition();
	}

	private void backToInitialXPosition() throws InterruptedException {
		while (psico.getPosition().x() != initialX) {
			left();
		}
	}

	private void backToInitialYPosition() throws InterruptedException {
		while (psico.getPosition().y() != initialY) {
			down();
		}
	}

	private void grabOneBall() throws InterruptedException {
		move();
		grab();
	}

}
