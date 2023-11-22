package papelaria.ideal.api.listaPendencia;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ListaPendencia")
public class ListaPendenciaController {

    @Autowired
    private ListaPendenciaRepository repository;

    @GetMapping
    public ResponseEntity<Page<DadosListagemListaPendencia>> listar(Pageable paginacao) {
        var page = repository.findAllByEntregueFalse(paginacao).map(DadosListagemListaPendencia::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var listaPendencia = repository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoListaPendencia(listaPendencia));
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
        var listaPendencia = repository.getReferenceById(id);
        listaPendencia.setEntregue(false);
        listaPendencia.setSituacao(SituacaoListaPendenciaEnum.CANCELADA);

        return ResponseEntity.noContent().build();
    }

}
