package br.com.edsonandrade.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.edsonandrade.model.Pessoa;
import br.com.edsonandrade.repository.PessoaRepository;

@Service 
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa pesquisarPessoa(Long codigo){
		Pessoa pessoa = pessoaRepository.findOne(codigo);
		if (pessoa == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoa;
	}

	public List<Pessoa> pesquisarTodas() {
		return pessoaRepository.findAll();
	}

	public br.com.edsonandrade.model.Pessoa salvar(Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}

	public Pessoa alterarPessoa(Pessoa pessoa, Long codigo) {
		Pessoa pessoaSalva = pesquisarPessoa(codigo);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		pessoaRepository.save(pessoaSalva);
		return pessoaSalva;
	}

	public void deletarPessoa(Long codigo) {
		pessoaRepository.delete(pesquisarPessoa(codigo));
	}

	public br.com.edsonandrade.model.Pessoa alterarStatusPessoa(Long codigo, Boolean ativo) {
		Pessoa pessoaSalva = pesquisarPessoa(codigo);
		pessoaSalva.setAtivo(ativo);
		return pessoaRepository.save(pessoaSalva);
		
	}
}
