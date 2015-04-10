package br.com.danielhabib.unit.core;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import br.com.danielhabib.core.Config;
import br.com.danielhabib.core.component.Environment;
import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.PsicoComponent;
import br.com.danielhabib.core.component.Wall;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;

@RunWith(ZohhakRunner.class)
public class EnvironmentTest {
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void itCanReadFromFile() throws Exception {
		File envFile = folder.newFile();
		write(envFile, "w  \n");
		write(envFile, " w \n");
		write(envFile, "  w");
		Environment environment = new Environment(envFile);

		assertThat(environment.hasWall(new Position(0, 0)), is(equalTo(true)));
	}

	private void write(File arquivo, String string) throws Exception {
		BufferedWriter out = new BufferedWriter(new FileWriter(arquivo, true));
		out.write(string);
		out.close();
	}

	@TestWith({ "", "' '" })
	public void getWalls_EmptyEnvironment_emptyList(String input) throws Exception {
		Environment environment = new Environment(input);

		assertThat(environment.hasWall(new Position(0, 0)), is(equalTo(false)));
	}

	@TestWith({ "w,1", "ww,2", "w w w,3" })
	public void getWalls_NWallsInOneLineOnly_OneLineEnvironment(String input, int expectedSize) throws Exception {
		Environment environment = new Environment(input);

		assertThat(environment.getComponentListForType('w'), hasSize(expectedSize));
	}

	@Test
	public void getWalls_SimpleWithBlanks() throws Exception {
		Environment environment = new Environment("w w");
		List<PsicoComponent> expected = Arrays.asList(
				newWallAtPosition(0, 0),
				newWallAtPosition(2 * 1, 0));

		List<PsicoComponent> walls = environment.getComponentListForType('w');

		assertThat(walls, hasSize(2));
		assertThat(walls, is(equalTo(expected)));
	}

	@Test
	public void getWalls_1WallDefaultSize() throws Exception {
		Environment environment = new Environment("w");
		List<PsicoComponent> expected = Arrays.asList(newWallAtPosition(0, 0));

		List<PsicoComponent> walls = environment.getComponentListForType('w');

		assertThat(walls, hasSize(1));
		assertThat(walls, is(equalTo(expected)));
	}

	@Test
	public void getWalls_2WallsDefaultSize() throws Exception {
		Environment environment = new Environment("ww");
		List<PsicoComponent> expected = Arrays.asList(
				newWallAtPosition(0, 0),
				newWallAtPosition(1, 0));

		List<PsicoComponent> walls = environment.getComponentListForType('w');

		assertThat(walls, hasSize(2));
		assertThat(walls, is(equalTo(expected)));
	}

	@Test
	public void getWalls_2WallOneOnTopOneUnder() throws Exception {
		Environment environment = new Environment("w\nw");
		List<PsicoComponent> expected = Arrays.asList(
				newWallAtPosition(0, 0),
				newWallAtPosition(0, 1));

		List<PsicoComponent> walls = environment.getComponentListForType('w');

		assertThat(walls, hasSize(2));
		assertThat(walls, is(equalTo(expected)));
	}

	@Test
	public void getWalls_3Walls2Floors() throws Exception {
		Environment environment = new Environment("w\nww\nw");
		List<PsicoComponent> expected = Arrays.asList(
				newWallAtPosition(0, 0),
				newWallAtPosition(0, 1),
				newWallAtPosition(1, 1),
				newWallAtPosition(0, 2 * 1));

		List<PsicoComponent> walls = environment.getComponentListForType('w');

		assertThat(walls, hasSize(4));
		assertThat(walls, is(equalTo(expected)));
	}

	@Test
	public void getBall_NoBall_EmptyList() throws Exception {
		Environment environment = new Environment("");

		List<PsicoComponent> balls = environment.getComponentListForType('o');

		assertThat(balls, hasSize(0));
	}

	@Test
	public void getBall_1Ball_ListWithBall() throws Exception {
		Environment environment = new Environment("o");

		List<PsicoComponent> balls = environment.getComponentListForType('o');

		assertThat(balls, hasSize(1));
	}

	@Test
	public void getGoal_1Goal_ListWithGoals() throws Exception {
		Environment environment = new Environment("g");

		List<PsicoComponent> goals = environment.getComponentListForType('g');

		assertThat(goals, hasSize(1));
	}

	private PsicoComponent newWallAtPosition(int x, int y) {
		return new Wall(new Position(x, y), Config.SIZE);
	}

}
