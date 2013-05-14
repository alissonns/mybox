package br.gov.serpro.demoiselle.extra.exception;

import br.gov.frameworkdemoiselle.exception.ApplicationException;
import br.gov.frameworkdemoiselle.message.Message;
import br.gov.frameworkdemoiselle.message.SeverityType;

@ApplicationException
public class AplicacaoException extends AbstractException {

	private static final long serialVersionUID = 1L;

	public AplicacaoException() {
	}

	public AplicacaoException(Throwable cause) {
		super(cause);
	}

	public AplicacaoException(String message, Object... params) {
		this(message, SeverityType.FATAL, params);
	}

	public AplicacaoException(String message, SeverityType severity, Object... params) {
		super(message, severity, params);
	}

	public AplicacaoException(String message, Throwable cause, Object... params) {
		this(message, SeverityType.FATAL, cause, params);
	}

	public AplicacaoException(String message, SeverityType severity, Throwable cause, Object... params) {
		super(message, severity, cause, params);
	}

	public AplicacaoException(Message message, Object... params) {
		super(message, params);
	}

	public AplicacaoException(Message message, Throwable cause, Object... params) {
		super(message, cause, params);
	}
}
