package br.com.danielhabib.core;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.awt.Color;
import java.awt.Graphics;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PsicoComponentContainerTest {

	@Mock
	Graphics g;

	PsicoComponentContainer container;
	static final Position ORIGIN = new Position(0, 0);

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		container = new PsicoComponentContainer(ORIGIN);
	}

	@Test
	public void pop_emptyContainer_CantPop_ReturnsDefault() throws Exception {

		PsicoComponent component = container.pop();

		assertEquals(new NullComponent(), component);
	}

	@Test
	public void pushPop_hasOneBall_CanPop_ReturnsTheBall() throws Exception {
		PsicoComponent ball = new Ball(ORIGIN);

		container.push(ball);

		assertSame(ball, container.pop());
	}

	@Test
	public void pushPop_hasMoreBalls_CanPop_ReturnsTheBalls() throws Exception {
		PsicoComponent ball1 = new Ball(ORIGIN);
		PsicoComponent ball2 = new Ball(ORIGIN);

		container.push(ball1);
		container.push(ball2);

		assertSame(ball2, container.pop());
		assertSame(ball1, container.pop());
		assertEquals(new NullComponent(), container.pop());
	}

	@Test
	public void draw_NoBalls_Nothing() throws Exception {
		container.draw(g);

		verifyZeroInteractions(g);
	}

	@Test
	public void draw_OneBalls_DrawsIt() throws Exception {
		PsicoComponent ball = spy(new Ball(ORIGIN));
		container.push(ball);

		container.draw(g);

		verify(ball).draw(g);
	}

	@Test
	public void draw_MoreBalls_DrawsThem() throws Exception {
		PsicoComponent ball = spy(new Ball(ORIGIN));
		PsicoComponent ball2 = spy(new Ball(ORIGIN));
		container.push(ball);
		container.push(ball2);

		container.draw(g);

		verify(g, times(2)).fillOval(anyInt(), anyInt(), anyInt(), anyInt());
	}

	@Test
	public void getColorBuilder_hasNullColorBuilder() throws Exception {
		IColorBuilder colorBuilder = container.getColorBuilder();

		assertTrue(colorBuilder instanceof NullColorBuilder);
	}

	@Test
	public void canSetColorBuilder() throws Exception {
		container.push(new Ball(ORIGIN));
		container.push(new Ball(ORIGIN));

		container.setColorBuilder(new ColorBuilder(new Color[] { Color.RED, Color.BLUE }));

		container.draw(g);
		verify(g).setColor(Color.RED);
		verify(g).setColor(Color.BLUE);
	}

}
