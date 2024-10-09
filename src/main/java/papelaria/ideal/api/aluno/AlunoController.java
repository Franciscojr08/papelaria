package papelaria.ideal.api.aluno;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import papelaria.ideal.api.Serie.records.DadosComboSerie;
import papelaria.ideal.api.aluno.records.*;
import papelaria.ideal.api.errors.DadosResponse;
import papelaria.ideal.api.errors.ValidacaoException;

import java.time.LocalDateTime;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private AlunoService alunoService;


    @PostMapping
    @Transactional
    public ResponseEntity<DadosResponse> cadastrar(@RequestBody @Valid DadosCadastroAluno dados) {
        alunoService.cadastrar(dados);
        var dadosResponse = new DadosResponse(
                LocalDateTime.now(),
                "Sucesso",
                HttpStatus.OK.value(),
                "Aluno cadastrado com sucesso!"
        );

        return ResponseEntity.ok().body(dadosResponse);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemAluno>> listar(Pageable paginacao) {
        var page = alunoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemAluno::new);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/listar-por-turma/{turmaId}")
    public ResponseEntity<Page<DadosListagemAluno>> listarPorTurma(
            @PathVariable Long turmaId,
            Pageable pageable
    ) {
        var page = alunoRepository.findAllByAtivoTrueAndTurmaId(turmaId, pageable).map(DadosListagemAluno::new);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/listar-por-cliente/{clienteId}")
    public ResponseEntity<Page<DadosListagemAluno>> listarPorCliente(
            @PathVariable Long clienteId,
            Pageable pageable
    ) {
        var page = alunoRepository.findAllByAtivoTrueAndClienteId(clienteId, pageable).map(DadosListagemAluno::new);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/filtrar")
    public ResponseEntity<Page<DadosListagemAluno>> filtrar(
            Pageable pageable,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String matricula,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) String rg,
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) Long turmaId
    ) {
        var dadosFiltrs = new DadosFiltragemAluno(nome, matricula, cpf, rg, clienteId, turmaId);
        var page = alunoService.filtrar(dadosFiltrs,pageable).map(DadosListagemAluno::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoAluno> detalhar(@PathVariable Long id) {
        if (!alunoRepository.existsByIdAndAtivoTrue(id)) {
            throw new ValidacaoException("Aluno não encontrado ou inativo.");
        }

        return ResponseEntity.ok().body(new DadosDetalhamentoAluno(alunoRepository.getReferenceById(id)));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoAluno> atualizar(@RequestBody @Valid DadosAtualizacaoAluno dados) {
        if (!alunoRepository.existsByIdAndAtivoTrue(dados.id())) {
            throw new ValidacaoException("Aluno não encontrado ou inativo.");
        }

        alunoService.atualizarInformacoes(alunoRepository.getReferenceById(dados.id()),dados);

        return ResponseEntity.ok().body(new DadosDetalhamentoAluno(alunoRepository.getReferenceById(dados.id())));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosResponse> deletar(@PathVariable Long id) {
        if (!alunoRepository.existsByIdAndAtivoTrue(id)) {
            throw new ValidacaoException("Aluno não encontrado ou inativo.");
        }

        var aluno = alunoRepository.getReferenceById(id);
        aluno.setAtivo(false);

        var dadosResponse = new DadosResponse(
                LocalDateTime.now(),
                "Sucesso",
                HttpStatus.OK.value(),
                "Aluno deletado com sucesso!"
        );

        return ResponseEntity.ok().body(dadosResponse);
    }
}


