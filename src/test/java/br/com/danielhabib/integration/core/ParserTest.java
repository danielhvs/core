package br.com.danielhabib.integration.core;

import java.io.IOException;

import br.com.danielhabib.core.Config;
import br.com.danielhabib.core.Environment;
import br.com.danielhabib.core.Position;
import br.com.danielhabib.core.builder.LevelParser;

public class ParserTest extends AbstractIntegrationTest {

	@Override
	protected void testIt() throws Exception {

	}

	@Override
	protected Position psicoInitialPosition() {
		return new Position(0, Config.SIZE);
	}

	@Override
	protected Environment setupEnv() throws IOException {
		LevelParser parser = new LevelParser("w:0,0\nr:1,0-2,0");
		Environment env = new Environment();
		env.setBalls(parser.getBalls());
		env.setWalls(parser.getWalls());
		env.setGoals(parser.getGoals());
		return env;
	}

	@Override
	protected int setTimeoutMillis() {
		return 2000;
	}

}
