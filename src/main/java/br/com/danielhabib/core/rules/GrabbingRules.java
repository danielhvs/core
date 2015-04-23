package br.com.danielhabib.core.rules;

import java.util.ArrayList;
import java.util.List;

import br.com.danielhabib.core.builder.LevelParser;
import br.com.danielhabib.core.component.Component;
import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.nulls.NullComponent;
import br.com.danielhabib.core.nulls.NullObserver;

public class GrabbingRules implements IGrabbingRules {

	private LevelParser levelParser;
	private List<GoalRule> goalRules;
	private IRulesObserver rulesObserver;

	public GrabbingRules() {
		this.rulesObserver = new NullObserver();
		this.goalRules = new ArrayList<GoalRule>();
	}

	public Component getBall(Position position) {
		return levelParser.popBallAt(position);
	}

	public Component dropBall(Position position, Component ball) {
		levelParser.addBall(position, ball);
		notifyIfLevelIsOver();
		return new NullComponent();
	}

	private void notifyIfLevelIsOver() {
		for (GoalRule rule : goalRules) {
			if (!rule.isLevelOver()) {
				return;
			}
		}
		try {
			rulesObserver.levelIsOver();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setGoalRules(List<GoalRule> rules) {
		this.goalRules = rules;
	}

	public void setObserver(IRulesObserver rulesObserver) {
		this.rulesObserver = rulesObserver;
	}

	public void setLevelParser(LevelParser levelParser) {
		this.levelParser = levelParser;
	}

}
