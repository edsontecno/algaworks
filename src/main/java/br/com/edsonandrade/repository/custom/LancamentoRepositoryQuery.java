package br.com.edsonandrade.repository.custom;

import java.util.List;

import br.com.edsonandrade.model.Lancamento;
import br.com.edsonandrade.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public List<Lancamento> filtrar(LancamentoFilter filter);
	
}
