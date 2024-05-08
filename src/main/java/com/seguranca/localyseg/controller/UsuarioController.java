package com.seguranca.localyseg.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seguranca.localyseg.dto.GetUsuario;
import com.seguranca.localyseg.dto.UsuarioRetorno;
import com.seguranca.localyseg.models.UserRole;
import com.seguranca.localyseg.models.Usuario;
import com.seguranca.localyseg.repository.UsuarioRepository;
import com.seguranca.localyseg.securityConfigurations.ResgistroDto;
import com.seguranca.localyseg.service.UsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/listAlunos")
	public ResponseEntity<?> listAluno(){
		List<Usuario> usuarios = usuarioRepository.findAllByRole(UserRole.ALUNO);
		List<UsuarioRetorno> retornos = new ArrayList<>();
		for(Usuario usuario: usuarios) {
			UsuarioRetorno retorno = new UsuarioRetorno(
					usuario.getId(), usuario.getLogin(), usuario.getRole().getNome(), usuario.getPessoa());
			retornos.add(retorno);
		}
		return new ResponseEntity<>(retornos, HttpStatus.OK);
	}
	
	@GetMapping("/listServidor")
	public ResponseEntity<?> listServidor(){
		List<Usuario> usuarios = usuarioRepository.findAllByRole(UserRole.SERVIDOR);
		List<UsuarioRetorno> retornos = new ArrayList<>();
		for(Usuario usuario: usuarios) {
			UsuarioRetorno retorno = new UsuarioRetorno(
					usuario.getId(),usuario.getLogin(), usuario.getRole().getNome(), usuario.getPessoa());
			retornos.add(retorno);
		}
		return new ResponseEntity<>(retornos, HttpStatus.OK);
	}
	
	@GetMapping("/listAdm")
	public ResponseEntity<?> listAdm(){
		List<Usuario> usuarios = usuarioRepository.findAllByRole(UserRole.ADMIN);
		List<UsuarioRetorno> retornos = new ArrayList<>();
		for(Usuario usuario: usuarios) {
			UsuarioRetorno retorno = new UsuarioRetorno(
				usuario.getId(),usuario.getLogin(), usuario.getRole().getNome(), usuario.getPessoa());
			retornos.add(retorno);
		}
		return new ResponseEntity<>(retornos, HttpStatus.OK);
	}
	@GetMapping("/get")
	public ResponseEntity<?> getUsuario(@RequestBody GetUsuario get){
		System.out.println("\n\n\nchgouuu");
		return new ResponseEntity<>(usuarioRepository.findById(get.id()),HttpStatus.OK);
	}
	
	@PutMapping("/atualizar/adm")
	public ResponseEntity<?> atualizarAdm(@RequestBody @Valid ResgistroDto registroUsuario){
		ResponseEntity<?> response = usuarioService.editar(registroUsuario, UserRole.ADMIN);
		return response;
	}
	
	@PutMapping("/atualizar/servidor")
	public ResponseEntity<?> atualizarServidor(@RequestBody @Valid ResgistroDto registroUsuario){
		ResponseEntity<?> response = usuarioService.editar(registroUsuario, UserRole.ADMIN);
		return response;
	}
	
	@PutMapping("/atualizar/aluno")
	public ResponseEntity<?> atualizarAluno(@RequestBody @Valid ResgistroDto registroUsuario){
		ResponseEntity<?> response = usuarioService.editar(registroUsuario, UserRole.ADMIN);
		return response;
	}
}
