package br.com.danielhabib.core;

public interface IMoveHandler {

	boolean move(Direction direction);

	Position getPosition();

	void setSpeed(int speed);

	boolean hasBall();

	PsicoComponent getBall();

}
