package br.com.edsonandrade.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.edsonandrade.model.Lancamento;
import br.com.edsonandrade.model.Pessoa;
import br.com.edsonandrade.repository.LancamentoRepository;
import br.com.edsonandrade.repository.filter.LancamentoFilter;
import br.com.edsonandrade.repository.projection.ResumoLancamento;
import br.com.edsonandrade.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaService pessoaService;
	
	public Lancamento pesquisarLancamento(Long codigo){
		Lancamento lancamento = lancamentoRepository.findOne(codigo);
		if (lancamento == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return lancamento;
	}

	public List<Lancamento> pesquisarTodas() {
		return lancamentoRepository.findAll();
	}

	public Lancamento salvar(Lancamento lancamento) {
		Pessoa pessoa = pessoaService.findOne(lancamento.getPessoa().getCodigo());
		
		if(pessoa != null && !pessoa.getAtivo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		
		return lancamentoRepository.save(lancamento);
	}

	public Lancamento alterarLancamento(Lancamento lancamento, Long codigo) {
		Lancamento lancamentoSalvo = pesquisarLancamento(codigo);
		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");
		lancamentoRepository.save(lancamentoSalvo);
		return lancamentoSalvo;
	}

	public void deletarLancamento(Long codigo) {
		lancamentoRepository.delete(pesquisarLancamento(codigo));
	}

	public Page<Lancamento> pesquisarComFiltro(LancamentoFilter filter, Pageable pageable) {
		return lancamentoRepository.filtrar(filter,pageable);
	}

	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoRepository.resumir(lancamentoFilter,pageable);
	}
	
}
