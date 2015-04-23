package br.com.danielhabib.core.builder;

import java.util.HashMap;
import java.util.Map;

import br.com.danielhabib.core.component.Position;
import br.com.danielhabib.core.component.Component;

public class ComponentBuilder {

	private Map<Character, ATypeBuilder> map = new HashMap<Character, ATypeBuilder>();

	public Component build(char type, Position position) {
		return map.get(type).build(position);
	}

	public void setMap(Map<Character, ATypeBuilder> map) {
		this.map = map;
	}

}
