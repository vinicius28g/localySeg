package com.seguranca.localyseg.dto;

public record RegistroEspacoLog(String dataHora, long digitalId, long espacoId, int tipoAbertura) {

	public String dataHora() {
		return dataHora;
	}

	public long digitalId() {
		return digitalId;
	}


	public long espacoId() {
		return espacoId;
	}

	public int tipoAbertura() {
		return tipoAbertura;
	}

}
