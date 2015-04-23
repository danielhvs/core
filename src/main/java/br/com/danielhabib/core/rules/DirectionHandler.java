package br.com.danielhabib.core.rules;

import java.util.HashMap;
import java.util.Map;

public class DirectionHandler implements IDirectionHandler {

	private Map<Integer, Integer> directionsMap = new HashMap<Integer, Integer>();

	public Integer turn(Integer direction) {
		return directionsMap.get(direction);
	}

	public void setDirectionsMap(Map<Integer, Integer> directionsMap) {
		this.directionsMap = directionsMap;
	}
}
