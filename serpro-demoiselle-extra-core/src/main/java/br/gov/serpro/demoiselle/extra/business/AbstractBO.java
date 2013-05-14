package br.gov.serpro.demoiselle.extra.business;

import java.io.Serializable;

import br.gov.frameworkdemoiselle.exception.ExceptionHandler;
import br.gov.serpro.demoiselle.extra.exception.AbstractException;
import br.gov.serpro.demoiselle.extra.exception.AmbienteException;

public abstract class AbstractBO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Método responsável pelo tratamento de exceção nos BOs.
	 */
	@ExceptionHandler
	public void tratarExcecao(Exception ex) throws Exception {

		// Caso seja um AbstractException o tratamento já foi feito então apenas relança a exceção.
		if (ex instanceof AbstractException) {
			throw (AbstractException) ex;

			// Caso contrário a mesma será relançada como uma AmbienteException.
		} else {
			throw new AmbienteException(ex);
		}
	}
}
