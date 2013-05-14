package br.gov.serpro.demoiselle.extra.constant;

public enum TypeOrderEnum {

	ASC(1, "Crescente"), DESC(2, "Decrescente");

	private final Integer codigo;

	private final String descricao;

	private TypeOrderEnum(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public boolean isCrescente() {
		return this.equals(TypeOrderEnum.ASC);
	}

	public boolean isDecrescente() {
		return this.equals(TypeOrderEnum.DESC);
	}

}
