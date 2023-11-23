package papelaria.ideal.api.Serie;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/serie")
public class SerieController {

	@Autowired
	private SerieService serieService;
	@Autowired
	private SerieRepository serieRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<String> cadastrar(@RequestBody @Valid DadosCadastroSerie dados) {
		serieService.cadastrar(dados);

		return ResponseEntity.ok().body("Série cadastrada com sucesso!");
	}

	@GetMapping
	public ResponseEntity<Page<DadosSerie>> listar(Pageable paginacao) {
		var page = serieRepository.findAllByAtivoTrue(paginacao).map(DadosSerie::new);

		return ResponseEntity.ok().body(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DadosSerie> detalhar(@PathVariable Long id) {
		var serie = serieRepository.getReferenceById(id);

		return ResponseEntity.ok().body(new DadosSerie(serie));
	}

	@PutMapping
	@Transactional
	public ResponseEntity<DadosSerie> atualizar(@RequestBody @Valid DadosAtualizacaoSerie dados) {
		var serie = serieRepository.getReferenceById(dados.id());
		serieService.atualizarInformacoes(serie,dados);

		return ResponseEntity.ok().body(new DadosSerie(serie));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity deletar(@PathVariable Long id) {
		var serie = serieRepository.getReferenceById(id);
		serie.setAtivo(false);
		serie.setDataAtualizacao(LocalDateTime.now());

		return ResponseEntity.noContent().build();
	}
}
