package br.com.danielhabib.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GoalRule {

	private Map<PsicoComponent, List<PsicoComponent>> map = new HashMap<PsicoComponent, List<PsicoComponent>>();

	public GoalRule(PsicoComponent ball, PsicoComponent goal) {
		map.put(goal, Arrays.asList(ball));
	}

	public GoalRule(List<PsicoComponent> balls, PsicoComponent goal) {
		map.put(goal, balls);

	}

	public boolean isLevelOver() {
		for (Entry<PsicoComponent, List<PsicoComponent>> entry : map.entrySet()) {
			List<PsicoComponent> balls = entry.getValue();
			PsicoComponent goal = entry.getKey();
			for (PsicoComponent ball : balls) {
				if (notSamePosition(ball, goal)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean notSamePosition(PsicoComponent ball, PsicoComponent goal) {
		return !ball.getPosition().equals(goal.getPosition());
	}

}
