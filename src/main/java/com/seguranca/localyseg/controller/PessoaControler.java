package com.seguranca.localyseg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.seguranca.localyseg.models.Pessoa;
import com.seguranca.localyseg.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoa")
public class PessoaControler {
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@PostMapping("/salvar")
	public ResponseEntity<?> salva(@RequestBody Pessoa pessoa) {
		pessoaRepository.save(pessoa);
		return new ResponseEntity<>(pessoa, HttpStatus.CREATED);
	}
	@GetExchange("/listAll")
	public ResponseEntity<?> listAll(){
		List<Pessoa> pessoas = pessoaRepository.findAll();
		return new ResponseEntity<>(pessoas,HttpStatus.OK);
	}
}
