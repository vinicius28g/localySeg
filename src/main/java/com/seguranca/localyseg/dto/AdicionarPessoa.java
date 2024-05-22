package com.seguranca.localyseg.dto;

public record AdicionarPessoa(long espacoId, long pessoaId) {

	public long espacoId() {
		return espacoId;
	}

	public long pessoaId() {
		return pessoaId;
	}



}
