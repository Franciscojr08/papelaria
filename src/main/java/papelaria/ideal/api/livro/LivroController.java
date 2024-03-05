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
import papelaria.ideal.api.livro.records.DadosAtualizacaoLivro;
import papelaria.ideal.api.livro.records.DadosCadastroLivro;
import papelaria.ideal.api.livro.records.DadosDetalhamentoLivro;
import papelaria.ideal.api.livro.records.DadosListagemLivro;

import java.time.LocalDateTime;

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

	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoLivro> detalhar(@PathVariable Long id) {
		if (!livroRepository.existsByIdAndAtivoTrue(id)) {
			throw new ValidacaoException("Livro não encontrado ou inativo.");
		}

		return ResponseEntity.ok().body(new DadosDetalhamentoLivro(livroRepository.getReferenceById(id)));
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
