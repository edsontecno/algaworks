package br.com.edsonandrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.edsonandrade.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
