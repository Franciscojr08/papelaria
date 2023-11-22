package papelaria.ideal.api.listaPendencia;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ListaPendencia")
public class ListaPendenciaController {
    @Autowired
    private ListaPendenciaRepository repository;

//    @PostMapping
//    @Transactional
//    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroListaPendencia dados, UriComponentsBuilder uriBuilder) {
//        var pendencia = new ListaPendencia(dados);
//        repository.save(pendencia);
//
//        var uri = uriBuilder.path("/pendencia/{id}").buildAndExpand(pendencia.getId()).toUri();
//        return ResponseEntity.created(uri).body(new DadosDetalhamentoListaPendencia(pendencia));
//    }
    @GetMapping
    public ResponseEntity<Page<DadosListagemListaPendencia>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByEntregueFalse(paginacao).map(DadosListagemListaPendencia::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Page<DadosListagemListaPendencia>> listarPorId(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
        var page = repository.findById(paginacao).map(DadosListagemListaPendencia::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoListaPendencia dados) {
        var listaPendencia = repository.getReferenceById(dados.id());
        listaPendencia.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoListaPendencia(listaPendencia));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id) {
//        repository.deleteBy(id);
        var listaPendencia = repository.getReferenceById(id);
        listaPendencia.excluir();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var listaPendencia = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoListaPendencia(listaPendencia));
    }
}
