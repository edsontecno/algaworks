package br.com.edsonandrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.edsonandrade.model.Lancamento;
import br.com.edsonandrade.repository.custom.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery{

}
