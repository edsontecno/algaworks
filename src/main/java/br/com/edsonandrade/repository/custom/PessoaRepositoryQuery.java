package br.com.edsonandrade.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.edsonandrade.model.Pessoa;
import br.com.edsonandrade.repository.filter.PessoaFilter;

public interface PessoaRepositoryQuery {

	public Page<Pessoa> filtrar(PessoaFilter filter, Pageable pageable);
}
