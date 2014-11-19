package br.com.danielhabib.core;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;

@RunWith(ZohhakRunner.class)
public class PsicoTest {
	private Psico psico;

	@Before
	public void setup() {
		psico = new Psico(new CounterClockWiseDirection(), new RegularMoveHandler(new Position(0, 0)));
	}

	@Test
	public void initialXDefault() throws Exception {
		assertThat(x(), is(equalTo(0)));
	}

	@Test
	public void initialYDefault() throws Exception {
		assertThat(y(), is(equalTo(0)));
	}

	@TestWith({ "0,RIGHT", "1,UP", "2,LEFT", "3,DOWN", "4,RIGHT", "5,UP" })
	public void turnChangesDirection(int qtdGiros, Direction esperado) throws Exception {
		for (int i = 0; i < qtdGiros; i++) {
			psico.turn();
		}
		assertThat(psico.getDirection(), is(equalTo(esperado)));
	}

	@Test
	public void moveRight() throws Exception {
		psico.move();
		assertThat(x(), is(equalTo(1)));
	}

	@Test
	public void moveLeft() throws Exception {
		psico.turn();
		psico.turn();
		psico.move();
		assertThat(x(), is(equalTo(-1)));
	}

	@Test
	public void moveUp() throws Exception {
		psico.turn();
		psico.move();
		assertThat(y(), is(equalTo(1)));
	}

	@Test
	public void moveDown() throws Exception {
		psico.turn();
		psico.turn();
		psico.turn();
		psico.move();
		assertThat(y(), is(equalTo(-1)));
	}

	private int y() {
		return psico.getPosition().getY();
	}

	private int x() {
		return psico.getPosition().getX();
	}

}
