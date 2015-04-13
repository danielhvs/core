package br.com.danielhabib.core.rules;

import java.util.HashMap;
import java.util.Map;

public class DirectionHandler implements IDirectionHandler {

	private Integer direction;
	private Map<Integer, Integer> directionsMap = new HashMap<Integer, Integer>();

	public void turn() {
		direction = directionsMap.get(direction);
	}

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public void setDirectionsMap(Map<Integer, Integer> directionsMap) {
		this.directionsMap = directionsMap;
	}
}
