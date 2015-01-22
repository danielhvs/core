package br.com.danielhabib.unit.core;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import br.com.danielhabib.core.builder.BallBuilder;
import br.com.danielhabib.core.builder.ColorBuilder;
import br.com.danielhabib.core.builder.GoalBuilder;
import br.com.danielhabib.core.builder.PsicoComponentBuilder;
import br.com.danielhabib.core.builder.WallBuilder;
import br.com.danielhabib.core.component.Ball;
import br.com.danielhabib.core.component.Goal;
import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.PsicoComponent;
import br.com.danielhabib.core.component.Wall;

public class PsicoComponentBuilderTest {

	private PsicoComponentBuilder builder;

	@Before
	public void setup() {
		builder = new PsicoComponentBuilder();
		builder.registerTypeBuilder('w', new WallBuilder(new ColorBuilder(new Color[] { Color.GREEN })));
		builder.registerTypeBuilder('o', new BallBuilder(new ColorBuilder(new Color[] { Color.BLUE })));
		builder.registerTypeBuilder('g', new GoalBuilder(new ColorBuilder(new Color[] { Color.ORANGE.darker() })));
	}

	@Test
	public void build_W_Wall() throws Exception {
		PsicoComponent component = builder.build('w', 0, 0);

		assertThat(component, is(instanceOf(Wall.class)));
		assertThat(component.getPosition(), is(equalTo(new Position(0, 0))));
	}

	@Test
	public void build_O_Ball() throws Exception {
		PsicoComponent component = builder.build('o', 0, 0);

		assertThat(component, is(instanceOf(Ball.class)));
		assertThat(component.getPosition(), is(equalTo(new Position(0, 0))));
	}

	@Test
	public void build_G_Goal() throws Exception {
		PsicoComponent component = builder.build('g', 0, 0);

		assertThat(component, is(instanceOf(Goal.class)));
		assertThat(component.getPosition(), is(equalTo(new Position(0, 0))));
	}

	@Test(expected = IllegalArgumentException.class)
	public void build_Unknown_Exception() throws Exception {
		PsicoComponentBuilder builder = new PsicoComponentBuilder();

		builder.build(' ', 0, 0);
	}

	@Test
	public void hasDefaultColorBuilder_ForG() throws Exception {
		PsicoComponent component = builder.build('g', 0, 0);

		assertThat(component.getColor(), is(instanceOf(Color.class)));
	}

	@Test
	public void hasDefaultColorBuilder_ForW() throws Exception {
		PsicoComponent component = builder.build('w', 0, 0);

		assertThat(component.getColor(), is(instanceOf(Color.class)));
	}

	@Test
	public void hasDefaultColorBuilder_ForO() throws Exception {
		PsicoComponent component = builder.build('o', 0, 0);

		assertThat(component.getColor(), is(instanceOf(Color.class)));
	}

	@Test
	public void canDefineColorBuilderForBalls() throws Exception {
		PsicoComponentBuilder builder = new PsicoComponentBuilder();
		builder.registerTypeBuilder('o', new BallBuilder(new ColorBuilder(new Color[] { Color.RED, Color.BLUE })));

		PsicoComponent component = builder.build('o', 0, 0);

		assertThat(component.getColor(), is(equalTo(Color.RED)));
	}

	@Test
	public void canDefineColorBuilderForWalls() throws Exception {
		PsicoComponentBuilder builder = new PsicoComponentBuilder();
		builder.registerTypeBuilder('w', new WallBuilder(new ColorBuilder(new Color[] { Color.RED, Color.BLUE })));

		PsicoComponent component = builder.build('w', 0, 0);

		assertThat(component.getColor(), is(equalTo(Color.RED)));
	}
}
