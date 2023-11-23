package papelaria.ideal.api.livro;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity<String> cadastrar(@RequestBody @Valid DadosCadastroLivro dados) {
		livroService.cadastrar(dados);

		return ResponseEntity.ok().body("Livro cadastrado com sucesso!");
	}

	@GetMapping
	public ResponseEntity<Page<DadosLivro>> listar(Pageable paginacao) {
		var page = livroRepository.findAllByAtivoTrue(paginacao).map(DadosLivro::new);

		return ResponseEntity.ok().body(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DadosLivro> detalhar(@PathVariable Long id) {
		var livro = livroRepository.getReferenceById(id);

		return ResponseEntity.ok().body(new DadosLivro(livro));
	}

	@PutMapping
	@Transactional
	public ResponseEntity<DadosLivro> atualizar(@RequestBody @Valid DadosAtualizacaoLivro dados) {
		var livro = livroRepository.getReferenceById(dados.id());
		livroService.atualizarInformacoes(livro,dados);

		return ResponseEntity.ok().body(new DadosLivro(livro));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity deletar(@PathVariable Long id) {
		var livro = livroRepository.getReferenceById(id);
		livro.setAtivo(false);
		livro.setDataAtualizacao(LocalDateTime.now());

		return ResponseEntity.noContent().build();
	}
}
