package com.seguranca.localyseg.controller;

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

import com.seguranca.localyseg.dto.AdicionarPessoa;
import com.seguranca.localyseg.dto.ErroRetorno;
import com.seguranca.localyseg.dto.EspacoCadastro;
import com.seguranca.localyseg.models.Espaco;
import com.seguranca.localyseg.models.Pessoa;
import com.seguranca.localyseg.repository.EspacoRepository;
import com.seguranca.localyseg.repository.PessoaRepository;

@Controller
@RequestMapping("/espaco")
public class EspacoController {
	@Autowired
	private EspacoRepository espacoRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@PostMapping("/registro")
	public ResponseEntity<?> registro(@RequestBody EspacoCadastro cadastro){
		Espaco espaco = espacoRepository.findByNome(cadastro.nome());
		if(espaco== null) {
			espaco = new Espaco();
		}
		espaco.setEndercoIp(cadastro.EnderecoIp());
		espaco.setNome(cadastro.nome());
		espacoRepository.save(espaco);
		return new ResponseEntity<>(espaco, HttpStatus.CREATED);
	}
	
	@PutMapping("/adicionarPessoa")
	public ResponseEntity<?> adicionarPessoa(@RequestBody AdicionarPessoa adicionarPessoa){
		Pessoa pessoa = pessoaRepository.findById(adicionarPessoa.pessoaId()).get();
		Espaco espaco = espacoRepository.findById(adicionarPessoa.espacoId()).get();
		if(pessoa.getAcesso() == null || pessoa.getAcesso()<0) {
			return new ResponseEntity<>(new ErroRetorno("digital não encontrada"), HttpStatus.NOT_FOUND);
		}
		espaco.addPessoas(pessoa);
		espacoRepository.save(espaco);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/listPessoas/espaco{id}")
	public ResponseEntity<?> listarPessoas(@PathVariable Long id){
		try {
			Espaco espaco = espacoRepository.findById(id).get();
			return new ResponseEntity<> (espaco.getPessoas(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<> (new ErroRetorno("id não encontrado"), HttpStatus.NOT_FOUND);		}
		
	}
	
	@PutMapping("/adicionarResponsavel")
	public ResponseEntity<?> adicionarResponsavel(@RequestBody AdicionarPessoa adicionarPessoa){
		Pessoa pessoa = pessoaRepository.findById(adicionarPessoa.pessoaId()).get();
		Espaco espaco = espacoRepository.findById(adicionarPessoa.espacoId()).get();
		if(pessoa.getAcesso() == null || pessoa.getAcesso()<0) {
			return new ResponseEntity<>(new ErroRetorno("digital não encontrada"), HttpStatus.NOT_FOUND);
		}
		espaco.addPessoas(pessoa);
		espaco.setResponsavel(pessoa);
		espacoRepository.save(espaco);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@GetMapping("/get{id}")
	public ResponseEntity<?> list(@PathVariable long id){
		
		return new ResponseEntity<>(espacoRepository.findById(id).get(), HttpStatus.OK);
	}
	
	@GetMapping("/listAll")
	public ResponseEntity<?> listAll(){
		
		return new ResponseEntity<>(espacoRepository.findAll(), HttpStatus.OK);
	}
	
}
