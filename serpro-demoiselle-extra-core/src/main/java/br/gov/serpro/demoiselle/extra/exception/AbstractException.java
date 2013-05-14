package br.gov.serpro.demoiselle.extra.exception;

import br.gov.frameworkdemoiselle.message.DefaultMessage;
import br.gov.frameworkdemoiselle.message.Message;
import br.gov.frameworkdemoiselle.message.SeverityType;

public abstract class AbstractException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Message message;

	public AbstractException() {
		super();
	}

	public AbstractException(Throwable cause) {
		super(cause);
	}

	public AbstractException(String message, SeverityType severity, Object... params) {
		this.message = new DefaultMessage(message, severity, params);
	}

	public AbstractException(String message, Throwable cause, SeverityType severity, Object... params) {
		super(cause);
		this.message = new DefaultMessage(message, severity, params);
	}

	public AbstractException(Message message, Object... params) {
		if (params != null && params.length > 0) {
			message = new DefaultMessage(message.getText(), message.getSeverity(), params);
		}
		this.message = message;
	}

	public AbstractException(Message message, Throwable cause, Object... params) {
		super(cause);
		if (params != null && params.length > 0) {
			message = new DefaultMessage(message.getText(), message.getSeverity(), params);
		}
		this.message = message;
	}

	@Override
	public String getMessage() {
		return (this.message == null ? super.getMessage() : this.message.getText());
	}

	public Message getDefaultMessage() {
		return this.message;
	}

}
