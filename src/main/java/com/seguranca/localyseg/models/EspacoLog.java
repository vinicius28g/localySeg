package com.seguranca.localyseg.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class EspacoLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private Date dataHora;
	@ManyToOne
	private Usuario usuario;
	@NotNull
	@ManyToOne
	private Espaco espaco;
	private TipoAbertura abertura;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getDataHora() {
		return dataHora;
	}
	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Espaco getEspaco() {
		return espaco;
	}
	public void setEspaco(Espaco espaco) {
		this.espaco = espaco;
	}
	public TipoAbertura getAbertura() {
		return abertura;
	}
	public void setAbertura(TipoAbertura abertura) {
		this.abertura = abertura;
	}
	
	

}
