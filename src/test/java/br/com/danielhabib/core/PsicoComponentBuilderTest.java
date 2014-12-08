package br.com.danielhabib.core;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class PsicoComponentBuilderTest {
	@Test
	public void build_W_Wall() throws Exception {
		PsicoComponentBuilder builder = new PsicoComponentBuilder();

		PsicoComponent component = builder.build('w', 0, 0);

		assertThat(component, is(instanceOf(Wall.class)));
		assertThat(component.getPosition(), is(equalTo(new Position(0, 0))));
	}

	@Test
	public void build_O_Ball() throws Exception {
		PsicoComponentBuilder builder = new PsicoComponentBuilder();

		PsicoComponent component = builder.build('o', 0, 0);

		assertThat(component, is(instanceOf(Ball.class)));
		assertThat(component.getPosition(), is(equalTo(new Position(0, 0))));
	}

	@Test(expected = IllegalArgumentException.class)
	public void build_Unknown_Exception() throws Exception {
		PsicoComponentBuilder builder = new PsicoComponentBuilder();

		builder.build(' ', 0, 0);
	}
}
