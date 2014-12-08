package br.com.danielhabib.core;

import java.util.Arrays;
import java.util.List;

public class PsicoComponentBuilder {

	private List<Character> valids = Arrays.asList('w', 'o');

	public PsicoComponent build(char type, int x, int y) {
		if (!valids.contains(type)) {
			throw new IllegalArgumentException(
					"It doesnt contains valid types: " + valids.toString());
		}

		return type == 'w' ? new Wall(new Position(x, y)) 
						   : new Ball(new Position(x, y));
	}

}
