package br.com.danielhabib.core.nulls;

import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.rules.RegularMoveHandler;


public class NullMoveHandler extends RegularMoveHandler {

	public NullMoveHandler() {
		super(new Position(-1, -1));
	}

}
