package br.com.danielhabib.core;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.awt.Color;

import org.junit.Test;

public class ColorBuilderTest {
	@Test
	public void build_FirstColor_HasColor() throws Exception {
		IColorBuilder builder = new ColorBuilder(new Color[] { Color.BLUE });

		Color color = builder.nextColor();

		assertThat(color, is(equalTo(Color.BLUE)));
	}

	@Test
	public void build_ManyColors_GetLastColor() throws Exception {
		IColorBuilder builder = new ColorBuilder(new Color[] { Color.BLUE, Color.RED, Color.PINK });

		builder.nextColor();
		builder.nextColor();
		Color color = builder.nextColor();

		assertThat(color, is(equalTo(Color.PINK)));
	}

	@Test
	public void build_ManyColors_CirculatesColors() throws Exception {
		IColorBuilder builder = new ColorBuilder(new Color[] { Color.BLUE, Color.RED, Color.PINK });

		builder.nextColor();
		builder.nextColor();
		builder.nextColor();
		builder.nextColor();
		Color color = builder.nextColor();

		assertThat(color, is(equalTo(Color.RED)));
	}
}
