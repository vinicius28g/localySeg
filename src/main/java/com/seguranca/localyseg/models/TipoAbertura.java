package com.seguranca.localyseg.models;

public enum TipoAbertura {
	FECHADO("Fechado", 0),
	ABERTO("Aberto", 1);
	
	private String nome;
	private int valor;
	
	private TipoAbertura(String nome, int valor) {
		this.nome = nome;
		this.valor = valor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
	 public static TipoAbertura getTipo(int valor) {
	    	for (TipoAbertura tipo : TipoAbertura.values()) {
	            if (tipo.valor == valor) {
	                return tipo;
	            }
	        }
	        throw new IllegalArgumentException("Número invalido");
	    	
	    }

}
