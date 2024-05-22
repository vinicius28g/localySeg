package com.seguranca.localyseg.dto;

public record EspacoCadastro(String nome, String EnderecoIp) {
	
	public String nome() {
		return nome;
	}

	public String EnderecoIp() {
		return EnderecoIp;
	}

}
