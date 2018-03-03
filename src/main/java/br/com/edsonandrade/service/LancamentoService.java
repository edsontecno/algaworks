package br.com.edsonandrade.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.edsonandrade.model.Lancamento;
import br.com.edsonandrade.repository.LancamentoRepository;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
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
}
