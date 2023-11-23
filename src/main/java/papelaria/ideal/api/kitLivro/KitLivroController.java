package papelaria.ideal.api.kitLivro;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/kitLivro")
public class KitLivroController {

	@Autowired
	private KitLivroService kitLivroService;
	@Autowired
	private KitLivroRepository kitLivroRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<String> cadastrar(@RequestBody @Valid DadosCadastroKitLivro dados) {
		kitLivroService.cadastrar(dados);

		return ResponseEntity.ok().body("Kit Livro cadastrado com sucesso!");
	}

	@GetMapping
	public ResponseEntity<Page<DadosKitLivro>> listar(Pageable paginacao) {
		var page = kitLivroRepository.findAllByAtivoTrue(paginacao).map(DadosKitLivro::new);

		return ResponseEntity.ok().body(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DadosKitLivro> detalhar(@PathVariable Long id) {
		var livro = kitLivroRepository.getReferenceById(id);

		return ResponseEntity.ok().body(new DadosKitLivro(livro));
	}

	@PutMapping
	@Transactional
	public ResponseEntity<DadosKitLivro> atualizar(@RequestBody @Valid DadosAtualizacaoKitLivro dados) {
		var kitLivro = kitLivroRepository.getReferenceById(dados.id());
		kitLivroService.atualizarInformacoes(kitLivro,dados);

		return ResponseEntity.ok().body(new DadosKitLivro(kitLivro));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity deletar(@PathVariable Long id) {
		var kitLivro = kitLivroRepository.getReferenceById(id);
		kitLivroService.deletar(kitLivro);

		return ResponseEntity.noContent().build();
	}
}
