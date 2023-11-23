package aluno;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private AlunoRepository alunoRepository;

    public AlunoController(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @GetMapping  //apenas aluno ativo
    public Page<Aluno> listarAlunosAtivos() {
        return alunoRepository.findByAtivoTrue();
    }
    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid DadosCadastroAluno dados) {
        AlunoService.cadastrar(dados);

        return ResponseEntity.ok().body("Aluno cadastrado com sucesso!");
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemAluno>> listar(Pageable paginacao) {
        var page = alunoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemAluno::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamanetoAluno> detalhar(@PathVariable Long id) {
        var aluno = alunoRepository.getReferenceById(id);

        return ResponseEntity.ok().body(new DadosDetalhamanetoAluno(aluno));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamanetoAluno> atualizar(@RequestBody @Valid DadosAtualizacaoAluno dados) {
        var aluno = alunoRepository.getReferenceById(dados.id());
        aluno.atualizarInformacoes(dados);

        return ResponseEntity.ok().body(new DadosDetalhamanetoAluno(aluno));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id) {
        var aluno = alunoRepository.getReferenceById(id);
        aluno.setAtivo(false);

        return ResponseEntity.noContent().build();
    }
}


