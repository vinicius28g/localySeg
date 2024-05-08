package com.seguranca.localyseg.models;

public enum UserRole {
	ADMIN("Admin", 1),
	SERVIDOR("Servidor", 2),
	ALUNO("Aluno", 3);
	
	private String nome;
	private int codigo;
	
	UserRole(String nome, int codigo) {
		this.nome = nome;
		this.codigo = codigo;
	}

	public String getNome() {
		return this.nome;
	}
	
	public int getCodigo() {
		return this.codigo;
	}

}
