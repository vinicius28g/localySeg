package com.seguranca.localyseg.securityConfigurations;

import com.seguranca.localyseg.models.UserRole;

public record LoginResponseDto(long id,String login, String pass,String token, UserRole role) {


	public long id() {
		return id;
	}

	public String login() {
		return login;
	}

	public String pass() {
		return pass;
	}

	public String token() {
		return token;
	}

	public UserRole role() {
		return role;
	}
	
	

}
