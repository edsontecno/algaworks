package br.com.edsonandrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.edsonandrade.model.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

}
