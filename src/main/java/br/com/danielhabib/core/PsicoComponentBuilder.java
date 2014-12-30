package br.com.danielhabib.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PsicoComponentBuilder {

	private List<Character> valids = new ArrayList<Character>();
	private Map<Character, TypeBuilder> map = new HashMap<Character, TypeBuilder>();

	public PsicoComponent build(char type, int x, int y) {
		validadeType(type);
		return map.get(type).build(x, y);
	}

	private void validadeType(char type) {
		if (!valids.contains(type)) {
			throw new IllegalArgumentException("It doesnt contains valid types: " + valids.toString());
		}
	}

	public void registerTypeBuilder(char type, TypeBuilder typeBuilder) {
		valids.add(type);
		map.put(type, typeBuilder);
	}

}
