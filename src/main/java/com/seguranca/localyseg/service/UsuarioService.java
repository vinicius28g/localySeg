package com.seguranca.localyseg.service;

import java.util.InputMismatchException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.seguranca.localyseg.dto.ErroRetorno;
import com.seguranca.localyseg.models.Pessoa;
import com.seguranca.localyseg.models.UserRole;
import com.seguranca.localyseg.models.Usuario;
import com.seguranca.localyseg.repository.PessoaRepository;
import com.seguranca.localyseg.repository.UsuarioRepository;
import com.seguranca.localyseg.securityConfigurations.ResgistroDto;

import jakarta.validation.ConstraintViolationException;

@Service
public class UsuarioService {
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	

	public ResponseEntity<?> registro(ResgistroDto registro, UserRole role) {

		if (this.usuarioRepository.findByLogin(registro.login()) != null)
			return new ResponseEntity<>(new ErroRetorno("login já existente"), HttpStatus.CONFLICT);
		
		if(registro.id()!=null && registro.id()>0) {
			
		}
		
		String encryptePassword = new BCryptPasswordEncoder().encode(registro.pass());
		Pessoa pessoa = new Pessoa();

		pessoa = registro.pessoa();
		var usuario = new Usuario(registro.login(), encryptePassword, role, pessoa);
		
		pessoaRepository.save(pessoa);
		usuarioRepository.save(usuario);

		return new ResponseEntity<>(usuario, HttpStatus.CREATED);
	}
	
	public ResponseEntity<?> editar(ResgistroDto registro, UserRole role) {
		
		Optional<Usuario> usuarioOptional = usuarioRepository.findById(registro.id());
		
		Usuario usuario = usuarioOptional.get();
		
		Usuario usuCompare = (Usuario) usuarioRepository.findByLogin(registro.login());

		if (usuCompare != null && usuCompare != usuario ) {
			return new ResponseEntity<>(new ErroRetorno("login já existente"), HttpStatus.CONFLICT);
		}
		
		
		String encryptePassword = new BCryptPasswordEncoder().encode(registro.pass());
		Pessoa pessoa =registro.pessoa() ;
		
		pessoa.setId(usuario.getPessoa().getId());
		usuario.setLogin(registro.login());
		usuario.setPass(encryptePassword);
		usuario.setRole(role);
		usuario.setPessoa(pessoa);
		
		pessoaRepository.save(pessoa);
		usuarioRepository.save(usuario);

		return new ResponseEntity<>(usuario, HttpStatus.OK);
	}

}
