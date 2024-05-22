package com.seguranca.localyseg.dto;

public record CadastroDigital(long pessoaId, long digitalId,long labId) {

	public long pessoaId() {
		return pessoaId;
	}

	public long digitalId() {
		return digitalId;
	}

	public long labId() {
		return labId;
	}

	

}
