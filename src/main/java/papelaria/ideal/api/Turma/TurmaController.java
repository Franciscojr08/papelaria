package papelaria.ideal.api.Turma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    @GetMapping
    public List<Turma> listarTurmas() {
        return turmaService.listarTurmas();
    }

    @GetMapping("/{id}")
    public Optional<Turma> listarPorId(@PathVariable int id) {
        return turmaService.listarPorId(id);
    }




    @PutMapping("/{id}")
    public Turma atualizarTurma(@PathVariable int id, @RequestBody Turma turma) {
        return turmaService.atualizarTurma(id, turma);
    }

    @DeleteMapping("/{id}")
    public void deletarTurma(@PathVariable int id) {
        turmaService.deletarTurma(id);
    }
}

