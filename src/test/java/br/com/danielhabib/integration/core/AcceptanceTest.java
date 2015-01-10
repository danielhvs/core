package br.com.danielhabib.integration.core;

import java.util.Arrays;
import java.util.List;

import br.com.danielhabib.core.Config;

public class AcceptanceTest extends AbstractIntegrationTest {

	@Override
	protected void testIt() throws Exception {
		move();
		grab();
		move();
		up();
		move();
		move();
		drop();
	}

	@Override
	protected List<String> levels() {
		return Arrays.asList(
				"w:0,0\nw:1,0\nw:2,0\nw:3,0\nw:4,0\nw:5,0\nw:6,0\n"
						+ "w:0,1\nw:6,1\n"
						+ "w:0,2\nw:6,2\n"
						+ "w:0,3\nw:4,3\nw:5,3\nw:6,3\np:1,3\nr:2,3-5,2\n"
						+ "w:0,4\nw:1,4\nw:2,4\nw:3,4\nw:4,4\nw:5,4\nw:6,4\n");
	}

	@Override
	protected int setTimeoutMillis() {
		return 25;
	}

	@Override
	protected int xWindowSize() {
		return Config.SIZE * 7;
	}

	@Override
	protected int yWindowSize() {
		return Config.SIZE * 6;
	}

}
