package com.seguranca.localyseg.models;

import java.util.Collection;
import java.util.List;

import javax.management.relation.Role;

import org.hibernate.bytecode.internal.bytebuddy.PrivateAccessorException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Usuario implements UserDetails{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String login;
	private String pass;
	private UserRole role;
	@OneToOne
	private Pessoa pessoa;
	
	public Usuario(){}
	
	public Usuario(String login, String pass, UserRole role, Pessoa pessoa) {
		super();
		this.login = login;
		this.pass = pass;
		this.role = role;
		this.pessoa = pessoa;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		switch (role.getCodigo()) {
		case 1: {
			return List.of(new SimpleGrantedAuthority(UserRole.ADMIN.getNome()),
					new SimpleGrantedAuthority(UserRole.SERVIDOR.getNome()),
					new SimpleGrantedAuthority(UserRole.ALUNO.getNome()));
		}
		case 2: {
			return List.of(new SimpleGrantedAuthority(UserRole.SERVIDOR.getNome()),
					new SimpleGrantedAuthority(UserRole.ALUNO.getNome()));
		}
		case 3: {	
			return List.of(new SimpleGrantedAuthority(UserRole.ALUNO.getNome()));
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + role.getCodigo());
		}
		
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.pass;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.login ;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	

}
