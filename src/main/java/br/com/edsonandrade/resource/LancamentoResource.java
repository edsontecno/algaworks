package br.com.edsonandrade.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edsonandrade.event.RecursoCriadoEvent;
import br.com.edsonandrade.exceptionhandler.SistemaHandler.Erro;
import br.com.edsonandrade.model.Lancamento;
import br.com.edsonandrade.repository.filter.LancamentoFilter;
import br.com.edsonandrade.service.LancamentoService;
import br.com.edsonandrade.service.exception.PessoaInexistenteOuInativaException;

@RestController
@RequestMapping("/lancamento")
public class LancamentoResource {
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public List<Lancamento> pesquisarTodas(){
		return lancamentoService.pesquisarTodas();
	}
	
	@GetMapping("/filtro")
	public List<Lancamento> pesquisarComFiltro(LancamentoFilter filter){
		return lancamentoService.pesquisarComFiltro(filter);
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
	public ResponseEntity<Lancamento> alterarLancamento(@PathVariable Long codigo, @Valid @RequestBody  Lancamento lancamento){
		Lancamento lancamentoAlterado = lancamentoService.alterarLancamento(lancamento,codigo);
		 return lancamentoAlterado != null ? ResponseEntity.ok(lancamentoAlterado) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	public void deletarLancamento(@PathVariable Long codigo){
		lancamentoService.deletarLancamento(codigo);
	}

	@ExceptionHandler({ PessoaInexistenteOuInativaException.class })
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {
		String mensagemUsuario = messageSource.getMessage("pessoa.inexistente", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
}
