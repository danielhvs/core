package br.com.danielhabib.integration.core;

import java.io.IOException;

import br.com.danielhabib.core.Config;
import br.com.danielhabib.core.Environment;
import br.com.danielhabib.core.Position;

public class Level1Test extends AbstractIntegrationTest {

	@Override
	protected void testIt() throws Exception {
		move();
		grab();
		move();
		drop();

		// TODO: Test if level is over.
	}

	@Override
	protected Position psicoInitialPosition() {
		return new Position(Config.SIZE, 0);
	}

	@Override
	protected Environment setupEnv() throws IOException {
		return new Environment("w og");
	}

	@Override
	protected int setTimeoutMillis() {
		return 500;
	}
}
