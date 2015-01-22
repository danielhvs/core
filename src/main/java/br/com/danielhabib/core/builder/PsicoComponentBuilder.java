package br.com.danielhabib.core.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.danielhabib.core.component.PsicoComponent;

public class PsicoComponentBuilder {

	private List<Character> valids = new ArrayList<Character>();
	private Map<Character, ATypeBuilder> map = new HashMap<Character, ATypeBuilder>();

	public PsicoComponent build(char type, int x, int y) {
		validadeType(type);
		return map.get(type).build(x, y);
	}

	private void validadeType(char type) {
		if (!valids.contains(type)) {
			throw new IllegalArgumentException("It doesnt contains valid types: " + valids.toString());
		}
	}

	public void registerTypeBuilder(char type, ATypeBuilder typeBuilder) {
		valids.add(type);
		map.put(type, typeBuilder);
	}

}
