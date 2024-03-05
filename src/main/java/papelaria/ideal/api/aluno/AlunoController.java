package papelaria.ideal.api.aluno;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import papelaria.ideal.api.aluno.records.DadosDetalhamentoAluno;
import papelaria.ideal.api.aluno.records.DadosListagemAluno;
import papelaria.ideal.api.aluno.records.DadosAtualizacaoAluno;
import papelaria.ideal.api.aluno.records.DadosCadastroAluno;
import papelaria.ideal.api.errors.DadosResponse;
import papelaria.ideal.api.errors.ValidacaoException;

import java.time.LocalDateTime;

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


