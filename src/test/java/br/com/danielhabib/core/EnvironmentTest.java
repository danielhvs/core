package br.com.danielhabib.core;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;

@RunWith(ZohhakRunner.class)
public class EnvironmentTest {
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	private static final int WALL_SIZE = Config.SIZE;

	@Test
	public void itCanReadFromFile() throws Exception {
		File envFile = folder.newFile();
		write(envFile, "w  \n");
		write(envFile, " w \n");
		write(envFile, "  w");
		Environment environment = new Environment(envFile);

		List<Wall> walls = environment.getWalls();

		assertThat(walls, hasSize(3));
	}

	private void write(File arquivo, String string) throws Exception {
		BufferedWriter out = new BufferedWriter(new FileWriter(arquivo, true));
		out.write(string);
		out.close();
	}

	@TestWith({ "", "' '" })
	public void getWalls_EmptyEnvironment_emptyList(String input) throws Exception {
		Environment environment = new Environment(input);

		List<Wall> walls = environment.getWalls();

		assertThat(walls, hasSize(0));
	}

	@TestWith({ "w,1", "ww,2", "w w w,3" })
	public void getWalls_NWallsInOneLineOnly_OneLineEnvironment(String input, int expectedSize) throws Exception {
		Environment environment = new Environment(input);

		List<Wall> walls = environment.getWalls();

		assertThat(walls, hasSize(expectedSize));
	}

	@Test
	public void getWalls_SimpleWithBlanks() throws Exception {
		Environment environment = new Environment("w w");
		List<Wall> expected = Arrays.asList(
				newWallAtPosition(0, 0),
				newWallAtPosition(2 * WALL_SIZE, 0));

		List<Wall> walls = environment.getWalls();

		assertThat(walls, hasSize(2));
		assertThat(walls, is(equalTo(expected)));
	}

	@Test
	public void getWalls_1WallDefaultSize() throws Exception {
		Environment environment = new Environment("w");
		List<Wall> expected = Arrays.asList(newWallAtPosition(0, 0));

		List<Wall> walls = environment.getWalls();

		assertThat(walls, hasSize(1));
		assertThat(walls, is(equalTo(expected)));
		assertThat(walls.get(0).getSize(), is(equalTo(WALL_SIZE)));
	}

	@Test
	public void getWalls_2WallsDefaultSize() throws Exception {
		Environment environment = new Environment("ww");
		List<Wall> expected = Arrays.asList(
				newWallAtPosition(0, 0),
				newWallAtPosition(WALL_SIZE, 0));

		List<Wall> walls = environment.getWalls();

		assertThat(walls, hasSize(2));
		assertThat(walls, is(equalTo(expected)));
	}

	@Test
	public void getWalls_2WallOneOnTopOneUnder() throws Exception {
		Environment environment = new Environment("w\nw");
		List<Wall> expected = Arrays.asList(
				newWallAtPosition(0, 0),
				newWallAtPosition(0, WALL_SIZE));

		List<Wall> walls = environment.getWalls();

		assertThat(walls, hasSize(2));
		assertThat(walls, is(equalTo(expected)));
	}

	@Test
	public void getWalls_3Walls2Floors() throws Exception {
		Environment environment = new Environment("w\nww\nw");
		List<Wall> expected = Arrays.asList(
				newWallAtPosition(0, 0),
				newWallAtPosition(0, WALL_SIZE),
				newWallAtPosition(WALL_SIZE, WALL_SIZE),
				newWallAtPosition(0, 2 * WALL_SIZE));

		List<Wall> walls = environment.getWalls();

		assertThat(walls, hasSize(4));
		assertThat(walls, is(equalTo(expected)));
	}

	private Wall newWallAtPosition(int x, int y) {
		return new Wall(new Position(x, y), WALL_SIZE);
	}

}
