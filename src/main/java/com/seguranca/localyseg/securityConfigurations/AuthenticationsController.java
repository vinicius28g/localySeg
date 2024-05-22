package com.seguranca.localyseg.securityConfigurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seguranca.localyseg.dto.ErroRetorno;
import com.seguranca.localyseg.models.Pessoa;
import com.seguranca.localyseg.models.UserRole;
import com.seguranca.localyseg.models.Usuario;
import com.seguranca.localyseg.repository.PessoaRepository;
import com.seguranca.localyseg.repository.UsuarioRepository;
import com.seguranca.localyseg.service.UsuarioService;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationsController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	PessoaRepository pessoaRepository;
	
	@Autowired
	TokenService tokenService;
	@Autowired
	UsuarioService usuarioService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDto authenticationDto){
		  try {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(authenticationDto.login(),
                		authenticationDto.pass());
		
		Authentication authenticate = this.authenticationManager
                .authenticate(usernamePasswordAuthenticationToken);
//		var userNamePassword = new UsernamePasswordAuthenticationToken(authenticationDto.login(), authenticationDto.pass());
//		var auth = this.authenticationManager.authenticate(userNamePassword);
		
		var usuario = (Usuario) authenticate.getPrincipal();
		
		var token = tokenService.generateToken(usuario);
		
		var loginResponse = new LoginResponseDto(usuario.getId(), usuario.getLogin(), usuario.getPass(), token,usuario.getRole());
		
		return new ResponseEntity<>(loginResponse, HttpStatus.OK);
		  }catch (org.springframework.security.core.AuthenticationException e) {
		        return new ResponseEntity<>(new ErroRetorno("Usuario ou senha invalido"), HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PostMapping("/registro/aluno")
	public ResponseEntity<?> registroAluno(@RequestBody @Valid ResgistroDto registroUsuario){
		ResponseEntity<?> response = usuarioService.registro(registroUsuario, UserRole.ALUNO);
		return response;
	}
	
	@PostMapping("/registro/servidor")
	public ResponseEntity<?> registroServidor(@RequestBody @Valid ResgistroDto registroUsuario){
//		if(pegarUsuario()!= UserRole.ADMIN) {
//    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//    	}
		
		ResponseEntity<?> response = usuarioService.registro(registroUsuario, UserRole.SERVIDOR);
		return response;
	}
	
	@PostMapping("/registro/adm")
	public ResponseEntity<?> registroAdm(@RequestBody @Valid ResgistroDto registroUsuario){
		ResponseEntity<?> response = usuarioService.registro(registroUsuario, UserRole.ADMIN);
		return response;
	}
	
	@PostMapping("/registro/all")
	public ResponseEntity<?> registroAll(@RequestBody @Valid ResgistroDto registroUsuario){
		if (this.usuarioRepository.findByLogin(registroUsuario.login()) != null)
			return new ResponseEntity<>(new ErroRetorno("login já existente"), HttpStatus.CONFLICT);
		
//		if(registroUsuario.id()!=null && registro.id()>0) {
//			
//		}
		UserRole userRole = UserRole.getTipo(registroUsuario.role());
		String encryptePassword = new BCryptPasswordEncoder().encode(registroUsuario.pass());
		Pessoa pessoa = new Pessoa();

		pessoa = registroUsuario.pessoa();
		var usuario = new Usuario(registroUsuario.login(), encryptePassword, userRole, pessoa);
		
		pessoaRepository.save(pessoa);
		usuarioRepository.save(usuario);

		return new ResponseEntity<>(usuario, HttpStatus.CREATED);
		
	}
	
//	 public UserRole pegarUsuario() {
//	    	//obtem a autenticação do usuario logado
//	    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//	    	if (authentication != null && authentication.isAuthenticated()) {
//	    		Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
//	    		return usuarioLogado.getRole();
//	    	}
//	    	return null;
//		}
}
