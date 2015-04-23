package br.com.danielhabib.core.rules;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.com.danielhabib.core.component.Component;

public class GoalRule {

	private Map<Component, List<Component>> map = new HashMap<Component, List<Component>>();

	public GoalRule(Component ball, Component goal) {
		map.put(goal, Arrays.asList(ball));
	}

	public GoalRule(List<Component> balls, Component goal) {
		map.put(goal, balls);

	}

	public boolean isLevelOver() {
		for (Entry<Component, List<Component>> entry : map.entrySet()) {
			List<Component> balls = entry.getValue();
			Component goal = entry.getKey();
			for (Component ball : balls) {
				if (notSamePosition(ball, goal)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean notSamePosition(Component ball, Component goal) {
		return !ball.getPosition().equals(goal.getPosition());
	}

}
