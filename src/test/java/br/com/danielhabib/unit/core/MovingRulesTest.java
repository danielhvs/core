package br.com.danielhabib.unit.core;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.danielhabib.core.builder.ComponentBuilder;
import br.com.danielhabib.core.builder.LevelParser;
import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.Wall;
import br.com.danielhabib.core.rules.AMovingRules;
import br.com.danielhabib.core.rules.MovingRules;

public class MovingRulesTest {

	private static final int CONFIG_SIZE = 64;

	@Rule
	public TemporaryFolder tmpDir = new TemporaryFolder();

	@Mock
	private ComponentBuilder componentBuilder;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void move_ThereIsAWall_DoesntMove() throws Exception {
		when(componentBuilder.build('w', new Position(1, 0))).thenReturn(new Wall(new Position(1, 0), CONFIG_SIZE));
		AMovingRules rules = newMoveHandlerWithEnv("w:1,0");

		Position position = rules.move(new Position(0, 0), new Position(1, 0));

		assertThat(position, is(equalTo(new Position(0, 0))));
	}

	@Test
	public void canMove_NoWall_CanMove() throws Exception {
		Position oneSizeToTheRight = new Position(1, 0);
		AMovingRules rules = newMoveHandlerWithEnv("");

		Position position = rules.move(new Position(0, 0), new Position(1, 0));

		assertThat(position, is(equalTo(oneSizeToTheRight)));
	}

	private AMovingRules newMoveHandlerWithEnv(String string) throws Exception {
		AMovingRules rules = new MovingRules();
		LevelParser levelParser = new LevelParser();

		File file = tmpDir.newFile();
		FileUtils.writeStringToFile(file, string);
		levelParser.setComponentBuilder(componentBuilder);
		levelParser.setFile(file);
		levelParser.build();
		rules.setLevelParser(levelParser);
		return rules;
	}
}