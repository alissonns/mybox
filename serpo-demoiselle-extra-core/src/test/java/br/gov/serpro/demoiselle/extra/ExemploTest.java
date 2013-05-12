package br.gov.serpro.demoiselle.extra;

import org.junit.Test;
import static org.junit.Assert.*;

public class ExemploTest {

	private Exemplo exemplo = new Exemplo();

	@Test
	public void testSunOK() {
		int sun = exemplo.sun(1, 2);
		assertEquals(3, sun);
	}

}
