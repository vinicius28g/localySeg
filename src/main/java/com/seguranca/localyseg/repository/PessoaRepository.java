package com.seguranca.localyseg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seguranca.localyseg.models.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
