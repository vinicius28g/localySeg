package com.seguranca.localyseg.dto;

import java.util.Date;

public record Buscalog(Integer role, Long usuarioId, Date dataInicio, Date dataFim, Long espacoId) {

	public Integer role() {
		return role;
	}

	public Long usuarioId() {
		return usuarioId;
	}

	public Date dataInicio() {
		return dataInicio;
	}

	public Date dataFim() {
		return dataFim;
	}

	public Long espacoId() {
		return espacoId;
	}
	

}
