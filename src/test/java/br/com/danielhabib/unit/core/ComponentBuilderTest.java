package br.com.danielhabib.unit.core;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import br.com.danielhabib.core.builder.ATypeBuilder;
import br.com.danielhabib.core.builder.BallBuilder;
import br.com.danielhabib.core.builder.ColorBuilder;
import br.com.danielhabib.core.builder.GoalBuilder;
import br.com.danielhabib.core.builder.ComponentBuilder;
import br.com.danielhabib.core.builder.WallBuilder;
import br.com.danielhabib.core.component.Ball;
import br.com.danielhabib.core.component.Goal;
import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.Component;
import br.com.danielhabib.core.component.Wall;

public class ComponentBuilderTest {

	private static final int CONFIG_SIZE = 64;
	private ComponentBuilder builder;

	@Before
	public void setup() {
		builder = new ComponentBuilder();
		Map<Character, ATypeBuilder> map = new HashMap<Character, ATypeBuilder>();
		WallBuilder wallBuilder = new WallBuilder();
		wallBuilder.setColorBuilder(new ColorBuilder(new Color[] { Color.GREEN }));
		wallBuilder.setSize(CONFIG_SIZE);
		BallBuilder ballBuilder = new BallBuilder();
		ballBuilder.setColorBuilder(new ColorBuilder(new Color[] { Color.BLUE }));
		ballBuilder.setSize(CONFIG_SIZE);
		GoalBuilder goalBuilder = new GoalBuilder();
		goalBuilder.setColorBuilder(new ColorBuilder(new Color[] { Color.ORANGE.darker() }));
		goalBuilder.setSize(CONFIG_SIZE);

		map.put('w', wallBuilder);
		map.put('o', ballBuilder);
		map.put('g', goalBuilder);

		builder.setMap(map);
	}

	@Test
	public void build_W_Wall() throws Exception {
		Component component = builder.build('w', new Position(0, 0));

		assertThat(component, is(instanceOf(Wall.class)));
		assertThat(component.getPosition(), is(equalTo(new Position(0, 0))));
	}

	@Test
	public void build_O_Ball() throws Exception {
		Component component = builder.build('o', new Position(0, 0));

		assertThat(component, is(instanceOf(Ball.class)));
		assertThat(component.getPosition(), is(equalTo(new Position(0, 0))));
	}

	@Test
	public void build_G_Goal() throws Exception {
		Component component = builder.build('g', new Position(0, 0));

		assertThat(component, is(instanceOf(Goal.class)));
		assertThat(component.getPosition(), is(equalTo(new Position(0, 0))));
	}

	@Test
	public void hasDefaultColorBuilder_ForG() throws Exception {
		Component component = builder.build('g', new Position(0, 0));

		assertThat(component.getColor(), is(instanceOf(Color.class)));
	}

	@Test
	public void hasDefaultColorBuilder_ForW() throws Exception {
		Component component = builder.build('w', new Position(0, 0));

		assertThat(component.getColor(), is(instanceOf(Color.class)));
	}

	@Test
	public void hasDefaultColorBuilder_ForO() throws Exception {
		Component component = builder.build('o', new Position(0, 0));

		assertThat(component.getColor(), is(instanceOf(Color.class)));
	}

}
