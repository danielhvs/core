package br.com.danielhabib.unit.core;

import static org.junit.Assert.assertEquals;

import org.junit.runner.RunWith;

import br.com.danielhabib.core.component.Position;

import com.googlecode.zohhak.api.Configure;
import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;

@RunWith(ZohhakRunner.class)
@Configure(separator = ";")
public class PositionTest {
	@TestWith({ "'0,1'; 0; 1", "'  1,2'; 1; 2", "'1,2  '; 1; 2", "'  1,2  '; 1; 2" })
	public void itCanInstantiateWithString(String string, int xExpected, int yExpected) {
		Position position = new Position(string);

		assertEquals(xExpected, position.x());
		assertEquals(yExpected, position.y());
	}
}
