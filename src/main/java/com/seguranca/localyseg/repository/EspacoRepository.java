package com.seguranca.localyseg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.seguranca.localyseg.models.Espaco;
import com.seguranca.localyseg.models.Pessoa;

public interface EspacoRepository extends JpaRepository<Espaco, Long>{
	Espaco findByNome(String nome);
	
	@Query("SELECT e FROM Espaco e WHERE e.responsavel = :pessoa OR :pessoa MEMBER OF e.pessoas")
	List<Espaco> findEspacosByPessoa(@Param("pessoa") Pessoa pessoa);

}
