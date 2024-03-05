package papelaria.ideal.api.Serie;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import papelaria.ideal.api.Serie.records.DadosAtualizacaoSerie;
import papelaria.ideal.api.Serie.records.DadosCadastroSerie;
import papelaria.ideal.api.Serie.records.DadosDetalhamentoSerie;
import papelaria.ideal.api.Serie.records.DadosListagemSerie;
import papelaria.ideal.api.errors.DadosResponse;
import papelaria.ideal.api.errors.ValidacaoException;

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
	public ResponseEntity<DadosResponse> cadastrar(@RequestBody @Valid DadosCadastroSerie dados) {
		serieService.cadastrar(dados);
		var dadosResponse = new DadosResponse(
				LocalDateTime.now(),
				"Sucesso",
				HttpStatus.OK.value(),
				"Série cadastrada com sucesso!"
		);

		return ResponseEntity.ok().body(dadosResponse);
	}

	@GetMapping
	public ResponseEntity<Page<DadosListagemSerie>> listar(Pageable paginacao) {
		var page = serieRepository.findAllByAtivoTrue(paginacao).map(DadosListagemSerie::new);

		return ResponseEntity.ok().body(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoSerie> detalhar(@PathVariable Long id) {
		if (!serieRepository.existsByIdAndAtivoTrue(id)) {
			throw new ValidacaoException("Série não encontrada ou inativa.");
		}

		return ResponseEntity.ok().body(new DadosDetalhamentoSerie(serieRepository.getReferenceById(id)));
	}

	@PutMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoSerie> atualizar(@RequestBody @Valid DadosAtualizacaoSerie dados) {
		if (!serieRepository.existsByIdAndAtivoTrue(dados.id())) {
			throw new ValidacaoException("Série não encontrada ou inativa.");
		}

		serieService.atualizarInformacoes(serieRepository.getReferenceById(dados.id()),dados);

		return ResponseEntity.ok().body(new DadosDetalhamentoSerie(serieRepository.getReferenceById(dados.id())));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<DadosResponse> deletar(@PathVariable Long id) {
		if (!serieRepository.existsByIdAndAtivoTrue(id)) {
			throw new ValidacaoException("Série não encontrada ou inativa.");
		}

		serieService.deletar(serieRepository.getReferenceById(id));

		var dadosResponse = new DadosResponse(
				LocalDateTime.now(),
				"Sucesso",
				HttpStatus.OK.value(),
				"Série deletada com sucesso!"
		);

		return ResponseEntity.ok().body(dadosResponse);
	}
}
