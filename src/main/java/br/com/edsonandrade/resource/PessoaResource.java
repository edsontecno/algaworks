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
import br.com.edsonandrade.model.Pessoa;
import br.com.edsonandrade.service.PessoaService;


@RestController
@RequestMapping("/pessoa")
public class PessoaResource {
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Pessoa> pesquisarTodas(){
		return pessoaService.pesquisarTodas();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> pesquisarPorId(@PathVariable  Long codigo){
		 Pessoa Pessoa = pessoaService.pesquisarPessoa(codigo);
		 return Pessoa != null ? ResponseEntity.ok(Pessoa) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa Pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = pessoaService.salvar(Pessoa);	
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> alterarPessoa(@PathVariable Long codigo, @Valid @RequestBody  Pessoa pessoa){
		 Pessoa Pessoa = pessoaService.alterarPessoa(pessoa,codigo);
		 return Pessoa != null ? ResponseEntity.ok(Pessoa) : ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{codigo}/ativo")
	public ResponseEntity<Pessoa> alterarStatusPessoa(@PathVariable Long codigo,@RequestBody  Boolean ativo){
		Pessoa Pessoa = pessoaService.alterarStatusPessoa(codigo,ativo);
		return Pessoa != null ? ResponseEntity.ok(Pessoa) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	public void deletarPessoa(@PathVariable Long codigo){
		pessoaService.deletarPessoa(codigo);
	}

}
