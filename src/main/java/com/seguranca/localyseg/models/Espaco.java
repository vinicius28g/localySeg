package com.seguranca.localyseg.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import com.seguranca.localyseg.models.Pessoa;

@Entity
public class Espaco {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(unique = true)
	@NotNull
	private String nome;
	@ManyToOne
	private Pessoa responsavel;
	@ManyToMany
	private Set<Pessoa> pessoas = new HashSet<>();
	@NotNull
	private String endercoIp;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Pessoa getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(Pessoa responsavel) {
		this.responsavel = responsavel;
	}
	public Set<Pessoa> getPessoas() {
		return pessoas;
	}
	public void setPessoas(Set<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	public String getEndercoIp() {
		return endercoIp;
	}
	public void setEndercoIp(String endercoIp) {
		this.endercoIp = endercoIp;
	}
	
	public void addPessoas(Pessoa pessoa) {
		this.pessoas.add(pessoa);
	}
	
	
	
}
