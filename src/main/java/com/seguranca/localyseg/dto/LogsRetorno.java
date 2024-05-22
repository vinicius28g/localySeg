package com.seguranca.localyseg.dto;

import java.util.Date;

public record LogsRetorno(long logId, String statusLog, Date dataHora, 
		String espaco, String user, String nome, String role) {

	public long logId() {
		return logId;
	}

	public String statusLog() {
		return statusLog;
	}

	public Date dataHora() {
		return dataHora;
	}

	public String espaco() {
		return espaco;
	}

	public String user() {
		return user;
	}

	public String nome() {
		return nome;
	}

	public String role() {
		return role;
	}

	
}
