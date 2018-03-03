package br.com.edsonandrade.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edsonandrade.event.RecursoCriadoEvent;
import br.com.edsonandrade.model.Lancamento;
import br.com.edsonandrade.model.Pessoa;
import br.com.edsonandrade.service.LancamentoService;

@RestController
@RequestMapping("/lancamento")
public class LancamentoResource {
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Lancamento> pesquisarTodas(){
		return lancamentoService.pesquisarTodas();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> pesquisarPorId(@PathVariable  Long codigo){
		Lancamento lancamento = lancamentoService.pesquisarLancamento(codigo);
		 return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento lancamentoSalvo = lancamentoService.salvar(lancamento);	
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Lancamento> alterarPessoa(@PathVariable Long codigo, @Valid @RequestBody  Lancamento pessoa){
		Lancamento Pessoa = lancamentoService.alterarLancamento(pessoa,codigo);
		 return Pessoa != null ? ResponseEntity.ok(Pessoa) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	public void deletarLancamento(@PathVariable Long codigo){
		lancamentoService.deletarLancamento(codigo);
	}

}
