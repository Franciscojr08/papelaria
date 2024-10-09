package papelaria.ideal.api.Turma;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import papelaria.ideal.api.Turma.records.*;
import papelaria.ideal.api.errors.DadosResponse;
import papelaria.ideal.api.errors.ValidacaoException;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/turma")
public class TurmaController {

	@Autowired
	private TurmaService turmaService;
	@Autowired
	private TurmaRepository turmaRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<DadosResponse> cadastrar(@RequestBody @Valid DadosCadastroTurma dados) {
		turmaService.cadastrar(dados);
		var dadosResponse = new DadosResponse(
				LocalDateTime.now(),
				"Sucesso",
				HttpStatus.OK.value(),
				"Turma cadastrada com sucesso!"
		);

		return ResponseEntity.ok().body(dadosResponse);
	}

	@GetMapping
	public ResponseEntity<Page<DadosListagemTurma>> listar(Pageable paginacao) {
		var page = turmaRepository.findAllByAtivoTrue(paginacao).map(DadosListagemTurma::new);

		return ResponseEntity.ok().body(page);
	}

	@GetMapping("/combo")
	public ResponseEntity<List<DadosComboTurma>> combo() {
		var combo = turmaService.montarCombo();

		return ResponseEntity.ok(combo);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoTurma> detalhar(@PathVariable Long id) {
		if (!turmaRepository.existsByIdAndAtivoTrue(id)) {
			throw new ValidacaoException("Turma não encontrada ou inativa.");
		}

		return ResponseEntity.ok().body(new DadosDetalhamentoTurma(turmaRepository.getReferenceById(id)));
	}

	@GetMapping("/filtrar")
	public ResponseEntity<Page<DadosListagemTurma>> filtrar(
			Pageable paginacao,
			@RequestParam(required = false) String nome,
			@RequestParam(required = false) Long serieId
	) {
		var filtros = new DadosFiltragemTurma(nome,serieId);
		var page = turmaService.filtrar(filtros,paginacao).map(DadosListagemTurma::new);

		return ResponseEntity.ok().body(page);
	}


	@GetMapping("/listar-por-serie/{serieId}")
	public ResponseEntity<Page<DadosListagemTurma>> listarPorSerie(
			@PathVariable Long serieId,
			Pageable pageable
	) {
		var page = turmaRepository.findBySerieId(serieId, pageable).map(DadosListagemTurma::new);

		return ResponseEntity.ok().body(page);
	}

	@PutMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoTurma> atualizar(@RequestBody @Valid DadosAtualizacaoTurma dados) {
		if (!turmaRepository.existsByIdAndAtivoTrue(dados.id())) {
			throw new ValidacaoException("Turma não encontrada ou inativa.");
		}

		turmaService.atualizarInformacoes(turmaRepository.getReferenceById(dados.id()),dados);

		return ResponseEntity.ok().body(new DadosDetalhamentoTurma(turmaRepository.getReferenceById(dados.id())));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<DadosResponse> deletar(@PathVariable Long id) {
		if (!turmaRepository.existsByIdAndAtivoTrue(id)) {
			throw new ValidacaoException("Turma não encontrada ou inativa.");
		}

		turmaService.deletar(turmaRepository.getReferenceById(id));

		var dadosResponse = new DadosResponse(
				LocalDateTime.now(),
				"Sucesso",
				HttpStatus.OK.value(),
				"Turma deletada com sucesso!"
		);

		return ResponseEntity.ok().body(dadosResponse);
	}
}
