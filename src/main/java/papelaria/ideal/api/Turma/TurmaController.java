package papelaria.ideal.api.Turma;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import papelaria.ideal.api.Serie.DadosAtualizacaoSerie;
import papelaria.ideal.api.Serie.DadosSerie;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/turma")
public class TurmaController {

	@Autowired
	private TurmaService turmaService;
	@Autowired
	private TurmaRepository turmaRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<String> cadastrar(@RequestBody @Valid DadosCadastroTurma dados) {
		turmaService.cadastrar(dados);

		return ResponseEntity.ok().body("Turma cadastrada com sucesso!");
	}

	@GetMapping
	public ResponseEntity<Page<DadosTurma>> listar(Pageable paginacao) {
		var page = turmaRepository.findAllByAtivoTrue(paginacao).map(DadosTurma::new);

		return ResponseEntity.ok().body(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DadosTurma> detalhar(@PathVariable Long id) {
		var turma = turmaRepository.getReferenceById(id);

		return ResponseEntity.ok().body(new DadosTurma(turma));
	}

	@PutMapping
	@Transactional
	public ResponseEntity<DadosTurma> atualizar(@RequestBody @Valid DadosAtualizacaoTurma dados) {
		var turma = turmaRepository.getReferenceById(dados.id());
		turmaService.atualizarInformacoes(turma,dados);

		return ResponseEntity.ok().body(new DadosTurma(turma));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity deletar(@PathVariable Long id) {
		var turma = turmaRepository.getReferenceById(id);
		turma.setAtivo(false);
		turma.setDataAtualizacao(LocalDateTime.now());

		return ResponseEntity.noContent().build();
	}
}
