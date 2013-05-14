package br.gov.serpro.demoiselle.extra.exception;

import br.gov.frameworkdemoiselle.message.Message;
import br.gov.frameworkdemoiselle.message.SeverityType;

public class AmbienteException extends AbstractException {

	private static final long serialVersionUID = 1L;

	public AmbienteException() {
	}

	public AmbienteException(Throwable cause) {
		super(cause);
	}

	public AmbienteException(String message, Object... params) {
		this(message, SeverityType.FATAL, params);
	}

	public AmbienteException(String message, SeverityType severity, Object... params) {
		super(message, severity, params);
	}

	public AmbienteException(String message, Throwable cause, Object... params) {
		this(message, SeverityType.FATAL, cause, params);
	}

	public AmbienteException(String message, SeverityType severity, Throwable cause, Object... params) {
		super(message, severity, cause, params);
	}

	public AmbienteException(Message message, Object... params) {
		super(message, params);
	}

	public AmbienteException(Message message, Throwable cause, Object... params) {
		super(message, cause, params);
	}

}
