package br.com.danielhabib.unit.core;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.danielhabib.core.component.Ball;
import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.PsicoComponent;
import br.com.danielhabib.core.component.PsicoComponentContainer;
import br.com.danielhabib.core.gui.Graphics;
import br.com.danielhabib.core.nulls.NullComponent;

public class PsicoComponentContainerTest {

	@Mock
	Graphics g;

	PsicoComponentContainer container;
	static final Position ORIGIN = new Position(0, 0);

	private static final int CONFIG_SIZE = 64;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		container = new PsicoComponentContainer(ORIGIN);
	}

	@Test
	public void draw_TwoBalls_DrawsLabelWithNumber() throws Exception {
		container.push(newBall(ORIGIN));
		container.push(newBall(ORIGIN));

		container.draw(g);

		verify(g).drawString(eq("2"), anyInt(), anyInt());
	}

	public Ball newBall(Position position) {
		return new Ball(position, CONFIG_SIZE / 2);
	}

	@Test
	public void push_ManyBalls_LastIsSmaller() throws Exception {
		container.push(newBall(ORIGIN));
		container.push(newBall(ORIGIN));
		container.push(newBall(ORIGIN));

		PsicoComponent thirdBall = container.get(2);
		PsicoComponent secondBall = container.get(1);
		PsicoComponent firstBall = container.get(0);

		assertThat(thirdBall.getSize(), is(lessThan(secondBall.getSize())));
		assertThat(secondBall.getSize(), is(lessThan(firstBall.getSize())));
	}

	@Test
	public void pop_emptyContainer_CantPop_ReturnsDefault() throws Exception {

		PsicoComponent component = container.pop();

		assertEquals(new NullComponent(), component);
	}

	@Test
	public void pop_HasSomeBalls_ReturnsBallWithDefaultSize() throws Exception {
		Ball thirdBall = newBall(ORIGIN);
		int originalSize = thirdBall.getSize();

		container.push(newBall(ORIGIN));
		container.push(newBall(ORIGIN));
		container.push(thirdBall);

		PsicoComponent ball = container.pop();

		assertEquals(originalSize, ball.getSize());
	}

	@Test
	public void pushPop_hasOneBall_CanPop_ReturnsTheBall() throws Exception {
		PsicoComponent ball = newBall(ORIGIN);

		container.push(ball);

		assertSame(ball, container.pop());
	}

	@Test
	public void pushPop_ManyBalls_UpdatesSize() throws Exception {
		PsicoComponent first = newBall(ORIGIN);
		PsicoComponent second = newBall(ORIGIN);
		int originalSize = second.getSize();

		container.push(first);
		container.push(second);

		int smallerSize = second.getSize();
		assertThat(smallerSize, is(lessThan(originalSize)));

		PsicoComponent ball2 = container.pop();

		container.push(ball2);
		assertThat(ball2.getSize(), is(equalTo(smallerSize)));
	}

	@Test
	public void pushPop_hasMoreBalls_CanPop_ReturnsTheBalls() throws Exception {
		PsicoComponent ball1 = newBall(ORIGIN);
		PsicoComponent ball2 = newBall(ORIGIN);

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
	public void draw_OneBall_DrawsIt() throws Exception {
		PsicoComponent ball = spy(newBall(ORIGIN));
		container.push(ball);

		container.draw(g);

		verify(ball).draw(g);
	}

	@Test
	public void draw_MoreBalls_DrawsThem() throws Exception {
		PsicoComponent ball = spy(newBall(ORIGIN));
		PsicoComponent ball2 = spy(newBall(ORIGIN));
		container.push(ball);
		container.push(ball2);

		container.draw(g);

		verify(g, times(2)).fillOval(anyInt(), anyInt(), anyInt(), anyInt());
	}

}
