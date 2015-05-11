package br.com.danielhabib.unit.core;

import static org.mockito.Mockito.verify;

import java.io.File;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.danielhabib.core.builder.LevelParser;
import br.com.danielhabib.core.component.Ball;
import br.com.danielhabib.core.component.Goal;
import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.rules.GoalRule;
import br.com.danielhabib.core.rules.GrabbingRules;
import br.com.danielhabib.core.rules.IGrabbingRules;
import br.com.danielhabib.core.rules.IRulesObserver;

import com.googlecode.zohhak.api.runners.ZohhakRunner;

@RunWith(ZohhakRunner.class)
public class GrabbingRulesTest {
	private static final int CONFIG_SIZE = 64;

	@Rule
	public TemporaryFolder tmpDir = new TemporaryFolder();

	@Mock
	private IRulesObserver observer;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void dropBall_LevelOver_Notifies() throws Exception {
		IGrabbingRules rules = newGrabbingRulesWithEnv("g:0,0");
		rules.setObserver(observer);

		Ball ball = new Ball(new Position(0, 0), CONFIG_SIZE);
		rules.setGoalRules(Arrays.asList(new GoalRule(ball, new Goal(new Position(0, 0), CONFIG_SIZE))));

		rules.dropBall(new Position(0, 0), ball);

		verify(observer).levelIsOver();
	}

	private IGrabbingRules newGrabbingRulesWithEnv(String string) throws Exception {
		GrabbingRules grabbingRules = new GrabbingRules();
		File file = tmpDir.newFile();
		FileUtils.writeStringToFile(file, string);
		LevelParser levelParser = new LevelParser();
		levelParser.setFile(file);
		grabbingRules.setLevelParser(levelParser);
		return grabbingRules;
	}

}
