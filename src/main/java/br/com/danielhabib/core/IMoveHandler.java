package br.com.danielhabib.core;

public interface IMoveHandler {

	boolean move(Direction direction);

	Position getPosition();

	boolean hasBall();

	PsicoComponent getBall();

	PsicoComponent dropBall();

}
