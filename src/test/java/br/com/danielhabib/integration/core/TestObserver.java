package br.com.danielhabib.integration.core;

import br.com.danielhabib.core.IRulesObserver;

public class TestObserver implements IRulesObserver {
	private boolean isOver = false;

	public boolean isOver() {
		return isOver;
	}

	public void levelIsOver() {
		isOver = true;
	}

	public void setNotOver() {
		isOver = false;
	}
}