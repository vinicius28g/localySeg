package com.seguranca.localyseg.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seguranca.localyseg.dto.Buscalog;
import com.seguranca.localyseg.dto.ErroRetorno;
import com.seguranca.localyseg.dto.LogsRetorno;
import com.seguranca.localyseg.dto.RegistroEspacoLog;
import com.seguranca.localyseg.models.Espaco;
import com.seguranca.localyseg.models.EspacoLog;
import com.seguranca.localyseg.models.Pessoa;
import com.seguranca.localyseg.models.TipoAbertura;
import com.seguranca.localyseg.models.UserRole;
import com.seguranca.localyseg.models.Usuario;
import com.seguranca.localyseg.repository.EspacoLogQueryRepository;
import com.seguranca.localyseg.repository.EspacoLogRepository;
import com.seguranca.localyseg.repository.EspacoRepository;
import com.seguranca.localyseg.repository.PessoaRepository;
import com.seguranca.localyseg.repository.UsuarioRepository;
import com.seguranca.localyseg.securityConfigurations.ResgistroDto;

@Controller
@RequestMapping("/espacoLog")
public class EspacoLogController {
	
	@Autowired
	private EspacoLogRepository espacoLogRepository;
	@Autowired
	private EspacoRepository espacoRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private EspacoLogQueryRepository espacoLogQueryRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@PostMapping("/registro")
	public ResponseEntity<?> registro(@RequestBody RegistroEspacoLog registro){
		EspacoLog log = new EspacoLog();
		Espaco espaco = espacoRepository.findById(registro.espacoId()).get();
		Pessoa pessoa = pessoaRepository.findByacesso(registro.digitalId());
		Usuario usuario = usuarioRepository.findByPessoa(pessoa);
		if(espaco == null) {
			return new ResponseEntity<>(new ErroRetorno("Espaco não encontrado"), HttpStatus.NOT_FOUND);
		}
		if(usuario == null) {
			return new ResponseEntity<>(new ErroRetorno("Usuario não encontrado"), HttpStatus.NOT_FOUND);
		}
		
		log.setDataHora(new Date());
		log.setEspaco(espaco);
		log.setUsuario(usuario);
		log.setAbertura(TipoAbertura.getTipo(registro.tipoAbertura()));
		espacoLogRepository.save(log);
		
		return new ResponseEntity<>(
				new LogsRetorno(log.getId(), 
				log.getAbertura().getNome(),
				log.getDataHora(), 
				log.getEspaco().getNome(),
				log.getUsuario().getLogin(),
				log.getUsuario().getPessoa().getNome(),
				log.getUsuario().getRole().getNome()), 
				HttpStatus.CREATED);
	}
	
	@PostMapping("/logs")
	public ResponseEntity<?> logs(@RequestBody Buscalog buscaLog){
		if(buscaLog.espacoId()== null || buscaLog.espacoId()<0){
			return new ResponseEntity<>(new ErroRetorno("id de espaco Obrigatorio"), HttpStatus.NOT_FOUND);
		}
		UserRole role = null;
		Usuario usuario = null;
		Espaco espaco = null;
		List<LogsRetorno> retornos = new ArrayList<>();
		
		if(buscaLog.usuarioId()!= null && buscaLog.usuarioId()>0)
			usuario = usuarioRepository.findById(buscaLog.usuarioId()).get();
		
		if(buscaLog.espacoId()!= null && buscaLog.espacoId()>0)
			espaco = espacoRepository.findById(buscaLog.espacoId()).get();
		
		if(buscaLog.role()!= null && buscaLog.role()>0) 
			role = UserRole.getTipo(buscaLog.role());
		
		List<EspacoLog> logs = espacoLogQueryRepository.buscaLog(role, usuario, buscaLog.dataInicio(), buscaLog.dataFim(), espaco);
		for(EspacoLog log: logs) {
			retornos.add(new LogsRetorno(log.getId(), 
					log.getAbertura().getNome(),
					log.getDataHora(), 
					log.getEspaco().getNome(),
					log.getUsuario().getLogin(),
					log.getUsuario().getPessoa().getNome(),
					log.getUsuario().getRole().getNome()));
		}
		return new ResponseEntity<>(retornos, HttpStatus.OK);
	}

}
