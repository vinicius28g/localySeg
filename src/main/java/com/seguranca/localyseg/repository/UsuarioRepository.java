package com.seguranca.localyseg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.seguranca.localyseg.models.Pessoa;
import com.seguranca.localyseg.models.UserRole;
import com.seguranca.localyseg.models.Usuario;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	UserDetails findByLogin(String login);
	
	List<Usuario>findAllByRole(UserRole role);
	
	Usuario findByPessoa(Pessoa pessoa);

}
