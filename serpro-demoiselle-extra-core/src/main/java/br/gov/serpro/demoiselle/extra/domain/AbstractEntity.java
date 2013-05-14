package br.gov.serpro.demoiselle.extra.domain;

import java.io.Serializable;

public abstract class AbstractEntity<I extends Serializable> implements Serializable {

	private static final long serialVersionUID = 1L;

	public abstract I getId();

}
