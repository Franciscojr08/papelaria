package papelaria.ideal.api.livro;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import papelaria.ideal.api.errors.DadosResponse;
import papelaria.ideal.api.errors.ValidacaoException;
import papelaria.ideal.api.livro.records.*;
import papelaria.ideal.api.pedido.records.DadosListagemPedido;

import java.time.LocalDateTime;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/livro")
public class LivroController {


	@Autowired
	private LivroService livroService;
	@Autowired
	private  LivroRepository livroRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<DadosResponse> cadastrar(@RequestBody @Valid DadosCadastroLivro dados) {
		livroService.cadastrar(dados);
		var dadosResponse = new DadosResponse(
				LocalDateTime.now(),
				"Sucesso",
				HttpStatus.OK.value(),
				"Livro cadastrado com sucesso!"
		);

		return ResponseEntity.ok().body(dadosResponse);
	}

	@GetMapping
	public ResponseEntity<Page<DadosListagemLivro>> listar(Pageable paginacao) {
		var page = livroRepository.findAllByAtivoTrue(paginacao).map(DadosListagemLivro::new);

		return ResponseEntity.ok().body(page);
	}

	@GetMapping("/filtrar")
	public ResponseEntity<Page<DadosListagemLivro>> filtrar(
			Pageable pageable,
			@RequestParam(required = false) String identificador,
			@RequestParam(required = false) String nome,
			@RequestParam(required = false) Long quantidadeDisponivel,
			@RequestParam(required = false) Long serieId,
			@RequestParam(required = false) Boolean usoInterno,
			@RequestParam(required = false) Float valor
	) {
		var filtros = new DadosFiltragemLivro(identificador, nome, quantidadeDisponivel, serieId, usoInterno, valor);
		var page = livroService.filtrar(filtros,pageable).map(DadosListagemLivro::new);

		return ResponseEntity.ok().body(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoLivro> detalhar(@PathVariable Long id) {
		if (!livroRepository.existsByIdAndAtivoTrue(id)) {
			throw new ValidacaoException("Livro não encontrado ou inativo.");
		}

		return ResponseEntity.ok().body(new DadosDetalhamentoLivro(livroRepository.getReferenceById(id)));
	}

	@GetMapping("/listar-por-serie/{serieId}")
	public ResponseEntity<Page<DadosListagemLivro>> listarPorSerie(
			@PathVariable Long serieId,
			Pageable pageable
	) {
		var livros = livroRepository.findBySerieId(serieId,pageable).map(DadosListagemLivro::new);
		return ResponseEntity.ok(livros);
	}

	@PutMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoLivro> atualizar(@RequestBody @Valid DadosAtualizacaoLivro dados) {
		if (!livroRepository.existsByIdAndAtivoTrue(dados.id())) {
			throw new ValidacaoException("Livro não encontrado ou inativo.");
		}

		livroService.atualizarInformacoes(livroRepository.getReferenceById(dados.id()),dados);

		return ResponseEntity.ok().body(new DadosDetalhamentoLivro(livroRepository.getReferenceById(dados.id())));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<DadosResponse> deletar(@PathVariable Long id) {
		if (!livroRepository.existsByIdAndAtivoTrue(id)) {
			throw new ValidacaoException("Livro não encontrado ou inativo.");
		}

		livroService.deletar(livroRepository.getReferenceById(id));

		var dadosResponse = new DadosResponse(
				LocalDateTime.now(),
				"Sucesso",
				HttpStatus.OK.value(),
				"Livro deletado com sucesso!"
		);

		return ResponseEntity.ok().body(dadosResponse);
	}
}
