package papelaria.ideal.api.aluno;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private AlunoService alunoService;


    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid DadosCadastroAluno dados) {
        alunoService.cadastrar(dados);

        return ResponseEntity.ok().body("Aluno cadastrado com sucesso!");
    }

    @GetMapping
    public ResponseEntity<Page<DadosAluno>> listar(Pageable paginacao) {
        var page = alunoRepository.findAllByAtivoTrue(paginacao).map(DadosAluno::new);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosAluno> detalhar(@PathVariable Long id) {
        var aluno = alunoRepository.getReferenceById(id);

        return ResponseEntity.ok().body(new DadosAluno(aluno));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosAluno> atualizar(@RequestBody @Valid DadosAtualizacaoAluno dados) {
        var aluno = alunoRepository.getReferenceById(dados.id());
        alunoService.atualizarInformacoes(aluno,dados);

        return ResponseEntity.ok().body(new DadosAluno(aluno));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id) {
        var aluno = alunoRepository.getReferenceById(id);
        aluno.setAtivo(false);

        return ResponseEntity.noContent().build();
    }
}


