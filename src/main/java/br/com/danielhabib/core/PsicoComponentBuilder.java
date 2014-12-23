package br.com.danielhabib.core;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PsicoComponentBuilder {

	private List<Character> valids = Arrays.asList('w', 'o');
	private Map<Character, ColorBuilder> map = new HashMap<Character, ColorBuilder>();

	public PsicoComponentBuilder() {
		map.put('o', new ColorBuilder(new Color[] { Color.BLUE }));
		map.put('w', new ColorBuilder(new Color[] { Color.GREEN }));
	}

	public PsicoComponent build(char type, int x, int y) {
		validadeType(type);
		Color color = map.get(type).nextColor();
		return type == 'w' ? newWall(x, y, color) : newBall(x, y, color);
	}

	private Ball newBall(int x, int y, Color color) {
		Ball ball = new Ball(new Position(x, y));
		ball.setColor(color);
		return ball;
	}

	private Wall newWall(int x, int y, Color color) {
		Wall wall = new Wall(new Position(x, y));
		wall.setColor(color);
		return wall;
	}

	public void setColorBuilder(char type, ColorBuilder colorBuilder) {
		validadeType(type);
		map.put(type, colorBuilder);
	}

	private void validadeType(char type) {
		if (!valids.contains(type)) {
			throw new IllegalArgumentException("It doesnt contains valid types: " + valids.toString());
		}
	}

}
