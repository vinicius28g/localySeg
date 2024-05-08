package com.seguranca.localyseg.securityConfigurations;

public record AuthenticationDto(String login, String pass) {

	public String login() {
		return login;
	}

	public String pass() {
		return pass;
	}

}
