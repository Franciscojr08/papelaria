package papelaria.ideal.api.kitLivro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import papelaria.ideal.api.kitLivro.model.KitLivro;
import papelaria.ideal.api.kitLivro.service.KitLivroService;
import papelaria.ideal.api.kitLivro.repository.KitLivroRepository;

import java.util.List;

@RestController
@RequestMapping("/kitslivros")
public class KitLivroController {

    @Autowired
    private KitLivroService kitLivroService;

    @GetMapping
    public List<KitLivro> listarKitsLivro() {
        return kitLivroService.consultarTodosKitsLivro();
    }

    @GetMapping("/ativos")
    public List<KitLivro> listarKitsLivroAtivos() {
        return kitLivroService.consultarKitsLivroAtivos();
    }

    @GetMapping("/{id}")
    public KitLivro buscarKitLivroPorId(@PathVariable Long id) {
        return kitLivroService.buscarKitLivroPorId(id);
    }

    @PostMapping
    public KitLivro cadastrarKitLivro(@RequestBody KitLivro kitLivro) {
        return kitLivroService.cadastrarKitLivro(kitLivro);
    }

    @PutMapping("/{id}/nome")
    public void atualizarNomeKitLivro(@PathVariable Long id, @RequestParam String novoNome) {
        kitLivroService.atualizarNomeKitLivro(id, novoNome);
    }

    @PutMapping("/{id}/descricao")
    public void atualizarDescricaoKitLivro(@PathVariable Long id, @RequestParam String novaDescricao) {
        kitLivroService.atualizarDescricaoKitLivro(id, novaDescricao);
    }

    @PutMapping("/{id}/valorUnitario")
    public void atualizarValorUnitarioKitLivro(@PathVariable Long id, @RequestParam Float novoValorUnitario) {
        kitLivroService.atualizarValorUnitarioKitLivro(id, novoValorUnitario);
    }

    @PutMapping("/{id}/quantidadeKit")
    public void atualizarQuantidadeKitLivro(@PathVariable Long id, @RequestParam Integer novaQuantidadeKit) {
        kitLivroService.atualizarQuantidadeKitLivro(id, novaQuantidadeKit);
    }

    @PutMapping("/{id}/excluir")
    public void excluirKitLivro(@PathVariable Long id) {
        kitLivroService.excluirKitLivro(id);
    }
}
