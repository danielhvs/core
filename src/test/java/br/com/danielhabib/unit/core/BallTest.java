package br.com.danielhabib.unit.core;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.danielhabib.core.Config;
import br.com.danielhabib.core.component.Ball;
import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.PsicoComponent;
import br.com.danielhabib.core.gui.Graphics;

public class BallTest {
	@Mock
	Graphics g;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void draw_HasAColor_DrawsTheColor() throws Exception {
		PsicoComponent ball = newBallAt(new Position(0, 0));

		ball.setColor(Color.RED);
		ball.draw(g);

		verify(g).setColor(Color.RED);
	}

	@Test
	public void draw_HasADefaultColor() throws Exception {
		PsicoComponent ball = newBallAt(new Position(0, 0));

		ball.draw(g);

		verify(g).setColor(Ball.DEFAULT_COLOR);
	}

	@Test
	public void draw_HasASize_DrawsWithSize() throws Exception {
		PsicoComponent ball = newBallAt(new Position(0, 0));
		int size = 50;

		ball.setSize(size);
		ball.draw(g);

		verify(g).fillOval(anyInt(), anyInt(), eq(size), eq(size));
	}

	private Ball newBallAt(Position position) {
		return new Ball(position, Config.SIZE);
	}
}
