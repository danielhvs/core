package br.com.danielhabib.core;

public interface IMoveHandler {

	void move(Direction direction);

	Position getPosition();

	void setSpeed(int speed);

}
