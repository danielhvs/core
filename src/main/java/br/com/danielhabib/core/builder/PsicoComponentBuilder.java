package br.com.danielhabib.core.builder;

import java.util.HashMap;
import java.util.Map;

import br.com.danielhabib.core.component.PsicoComponent;

public class PsicoComponentBuilder {

	private Map<Character, ATypeBuilder> map = new HashMap<Character, ATypeBuilder>();

	public PsicoComponent build(char type, int x, int y) {
		return map.get(type).build(x, y);
	}

	public void setMap(Map<Character, ATypeBuilder> map) {
		this.map = map;
	}

}
