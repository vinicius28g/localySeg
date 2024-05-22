package com.seguranca.localyseg.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.seguranca.localyseg.models.Espaco;
import com.seguranca.localyseg.models.Pessoa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class EspacoQueryRespository {
	@PersistenceContext
	private EntityManager em;
	
	public List<Espaco> listaPorPessoa(Pessoa pessoa){
		StringBuilder query = new StringBuilder("from Espaco e where e.pessoa = :espaco ||");
		
		return null;
	}
}
