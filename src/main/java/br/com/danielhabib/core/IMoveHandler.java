package br.com.danielhabib.core;

public interface IMoveHandler {

	boolean move(Direction direction);

	Position getPosition();

	PsicoComponent getBall();

	PsicoComponent dropBall(PsicoComponent ball);

}
