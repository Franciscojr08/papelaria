package papelaria.ideal.api.kitLivro;

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
import papelaria.ideal.api.kitLivro.records.*;

import java.time.LocalDateTime;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/kitLivro")
public class KitLivroController {

	@Autowired
	private KitLivroService kitLivroService;
	@Autowired
	private KitLivroRepository kitLivroRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<DadosResponse> cadastrar(@RequestBody @Valid DadosCadastroKitLivro dados) {
		kitLivroService.cadastrar(dados);
		var dadosResponse = new DadosResponse(
				LocalDateTime.now(),
				"Sucesso",
				HttpStatus.OK.value(),
				"Kit Livro cadastrado com sucesso!"
		);

		return ResponseEntity.ok().body(dadosResponse);
	}

	@GetMapping
	public ResponseEntity<Page<DadosListagemKitLivro>> listar(Pageable paginacao) {
		var page = kitLivroRepository.findAllByAtivoTrue(paginacao).map(DadosListagemKitLivro::new);

		return ResponseEntity.ok().body(page);
	}

	@GetMapping("/filtrar")
	public ResponseEntity<Page<DadosListagemKitLivro>> filtrar(
			Pageable pageable,
			@RequestParam(required = false) String nome,
			@RequestParam(required = false) Float valor,
			@RequestParam(required = false) Long quantidadeDisponivel
	) {
		var filtros = new DadosFiltragemKitLivro(nome,valor,quantidadeDisponivel);

		var page = kitLivroService.filtrar(filtros, pageable).map(DadosListagemKitLivro::new);

		return ResponseEntity.ok().body(page);
	}


	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoKitLivro> detalhar(@PathVariable Long id) {
		if (!kitLivroRepository.existsByIdAndAtivoTrue(id)) {
			throw new ValidacaoException("Kit Livro não encontrado ou inativo.");
		}

		return ResponseEntity.ok().body(new DadosDetalhamentoKitLivro(kitLivroRepository.getReferenceById(id)));
	}

	@PutMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoKitLivro> atualizar(@RequestBody @Valid DadosAtualizacaoKitLivro dados) {
		if (!kitLivroRepository.existsByIdAndAtivoTrue(dados.id())) {
			throw new ValidacaoException("Kit Livro não encontrado ou inativo.");
		}

		kitLivroService.atualizarInformacoes(kitLivroRepository.getReferenceById(dados.id()),dados);

		return ResponseEntity.ok().body(new DadosDetalhamentoKitLivro(kitLivroRepository.getReferenceById(dados.id())));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<DadosResponse> deletar(@PathVariable Long id) {
		if (!kitLivroRepository.existsByIdAndAtivoTrue(id)) {
			throw new ValidacaoException("Kit Livro não encontrado ou inativo.");
		}

		kitLivroService.deletar(kitLivroRepository.getReferenceById(id));
		var dadosResponse = new DadosResponse(
				LocalDateTime.now(),
				"Sucesso",
				HttpStatus.OK.value(),
				"Kit Livro deletado com sucesso!"
		);

		return ResponseEntity.ok().body(dadosResponse);
	}
}
