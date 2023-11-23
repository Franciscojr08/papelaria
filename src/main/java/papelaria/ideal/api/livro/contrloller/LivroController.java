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

    @PutMapping("/{id}/isbn")
    public void atualizarIsbnLivro(@PathVariable Long id, @RequestParam String novoIsbn) {
        livroService.atualizarIsbnLivro(id, novoIsbn);
    }

    @PutMapping("/{id}/valor")
    public void atualizarValorLivro(@PathVariable Long id, @RequestParam Float novoValor) {
        livroService.atualizarValorLivro(id, novoValor);
    }

    @PutMapping("/{id}/usoInterno")
    public void atualizarUsoInternoLivro(@PathVariable Long id, @RequestParam Boolean novoUsoInterno) {
        livroService.atualizarUsoInternoLivro(id, novoUsoInterno);
    }

    @PutMapping("/{id}/quantidade")
    public void atualizarQuantidadeLivro(@PathVariable Long id, @RequestParam Integer novaQuantidade) {
        livroService.atualizarQuantidadeLivro(id, novaQuantidade);
    }

    @PutMapping("/{id}/ativo")
    public void atualizarAtivoLivro(@PathVariable Long id, @RequestParam Boolean novoStatus) {
        livroService.atualizarAtivoLivro(id, novoStatus);
    }

    @PutMapping("/{id}/desativar")
    public void desativarLivro(@PathVariable Long id) {
        livroService.desativarLivro(id);
    }

}
