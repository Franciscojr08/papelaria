package papelaria.ideal.api.livro.contrloller;
import papelaria.ideal.api.livro.service.LivroService;
import papelaria.ideal.api.livro.livromodel.Livro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping
    public List<Livro> listarLivros() {
        return livroService.consultarTodosLivros();
    }

    @GetMapping("/ativos")
    public List<Livro> listarLivrosAtivos() {
        return livroService.consultarLivrosAtivos();
    }

    @GetMapping("/{id}")
    public Livro buscarLivroPorId(@PathVariable Long id) {
        return livroService.buscarLivroPorId(id);
    }

    @PostMapping
    public Livro cadastrarLivro(@RequestBody Livro livro) {
        return livroService.cadastrarLivro(livro);
    }

    @PutMapping("/{id}/nome")
    public void atualizarNomeLivro(@PathVariable Long id, @RequestParam String novoNome) {
        livroService.atualizarNomeLivro(id, novoNome);
    }

    // Demais métodos para atualizar outros atributos de livro
    // ...

    @DeleteMapping("/{id}")
    public void deletarLivro(@PathVariable Long id) {
        livroService.excluirLivro(id);
    }
}

