package br.com.danielhabib.core;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import java.awt.Color;
import java.awt.Graphics;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BallTest {
	@Mock
	Graphics g;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void draw_HasAColor_DrawsTheColor() throws Exception {
		PsicoComponent ball = new Ball(new Position(0, 0));

		ball.setColor(Color.RED);
		ball.draw(g);

		verify(g).setColor(Color.RED);
	}

	@Test
	public void draw_HasADefaultColor() throws Exception {
		PsicoComponent ball = new Ball(new Position(0, 0));

		ball.draw(g);

		verify(g).setColor(Ball.DEFAULT_COLOR);
	}

	@Test
	public void draw_HasADefaultSize() throws Exception {
		PsicoComponent ball = new Ball(new Position(0, 0));

		ball.draw(g);

		verify(g).fillOval(anyInt(), anyInt(), eq(Ball.DEFAULT_SIZE), eq(Ball.DEFAULT_SIZE));
	}

	@Test
	public void draw_HasASize_DrawsWithSize() throws Exception {
		PsicoComponent ball = new Ball(new Position(0, 0));
		int size = 50;

		ball.setSize(size);
		ball.draw(g);

		verify(g).fillOval(anyInt(), anyInt(), eq(size), eq(size));
	}
}
