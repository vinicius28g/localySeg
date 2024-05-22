package com.seguranca.localyseg.securityConfigurations;

import com.seguranca.localyseg.models.Pessoa;

public record ResgistroDto(Long id ,String login, String pass, Pessoa pessoa, int role) {

	public Long id() {
		return id;
	}

	public String login() {
		return login;
	}

	public String pass() {
		return pass;
	}

	public Pessoa pessoa() {
		return pessoa;
	}

	public int role() {
		return role;
	}
	
	

}
