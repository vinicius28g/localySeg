package com.seguranca.localyseg.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.seguranca.localyseg.dto.CadastroDigital;
import com.seguranca.localyseg.dto.ErroRetorno;
import com.seguranca.localyseg.models.Espaco;
import com.seguranca.localyseg.models.Pessoa;
import com.seguranca.localyseg.repository.EspacoRepository;
import com.seguranca.localyseg.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoa")
public class PessoaControler {
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private EspacoRepository espacoRepository;
	
	@PostMapping("/salvar")
	public ResponseEntity<?> salva(@RequestBody Pessoa pessoa) {
		pessoaRepository.save(pessoa);
		return new ResponseEntity<>(pessoa, HttpStatus.CREATED);
	}
	@GetMapping("/listAll")
	public ResponseEntity<?> listAll(){
		List<Pessoa> pessoas = pessoaRepository.findAll();
		return new ResponseEntity<>(pessoas,HttpStatus.OK);
	}
	@PutMapping("/cadastrarDigital")
	public ResponseEntity<?> adcDigital(@RequestBody CadastroDigital cadastroDigital){
		Pessoa pessoa = pessoaRepository.findById(cadastroDigital.pessoaId()).get();
		pessoa.setAcesso(cadastroDigital.digitalId());
		Espaco espaco = espacoRepository.findById(cadastroDigital.labId()).get();
		
		pessoaRepository.save(pessoa);
		espacoRepository.save(espaco);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/listEspaco{pessoaId}")
	public ResponseEntity<?> listEspaco(@PathVariable long pessoaId){
			try {
				 Pessoa pessoa = pessoaRepository.findById(pessoaId).orElseThrow(() -> new Exception("Pessoa não Encontrada"));
		            List<Espaco> espacos = espacoRepository.findEspacosByPessoa(pessoa);
		            return new ResponseEntity<>(espacos, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new ErroRetorno(e.getMessage()), HttpStatus.NOT_FOUND);
			
		}
	}
}
