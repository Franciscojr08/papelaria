package papelaria.ideal.api.listaPendencia;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import papelaria.ideal.api.errors.DadosResponse;
import papelaria.ideal.api.errors.ValidacaoException;
import papelaria.ideal.api.listaPendencia.records.DadosAtualizacaoListaPendencia;
import papelaria.ideal.api.listaPendencia.records.DadosCancelamentoListaPendencia;
import papelaria.ideal.api.listaPendencia.records.DadosDetalhamentoListaPendencia;
import papelaria.ideal.api.listaPendencia.records.DadosListagemListaPendencia;
import papelaria.ideal.api.pedido.records.DadosListagemPedido;

import java.time.LocalDateTime;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("lista-pendencia")
public class ListaPendenciaController {

    @Autowired
    private ListaPendenciaRepository listaPendenciaRepository;
    @Autowired
    private ListaPendenciaService listaPendenciaService;

    @GetMapping
    public ResponseEntity<Page<DadosListagemListaPendencia>> listar(Pageable paginacao) {
        var page = listaPendenciaRepository.findAllByAtivoTrue(paginacao).map(DadosListagemListaPendencia::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoListaPendencia> detalhar(@PathVariable Long id) {
        if (!listaPendenciaRepository.existsByIdAndAtivoTrue(id)) {
            throw new ValidacaoException("Lista de pendência não encontrada ou inativa.");
        }

        return ResponseEntity.ok().body(new DadosDetalhamentoListaPendencia(listaPendenciaRepository.getReferenceById(id)));
    }

    @GetMapping("/listar-por-kit/{kitLivroId}")
    public ResponseEntity<Page<DadosListagemListaPendencia>> listarPedidosPorKitLivro(
            @PathVariable Long kitLivroId,
            Pageable pageable
    ) {
        Page<DadosListagemListaPendencia> listaPendencia = listaPendenciaService.listarPedidosPorKitLivro(
                kitLivroId,
                pageable
        );
        return ResponseEntity.ok(listaPendencia);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoListaPendencia> atualizar(@RequestBody @Valid DadosAtualizacaoListaPendencia dados) {
        if (!listaPendenciaRepository.existsByIdAndAtivoTrue(dados.id())) {
            throw new ValidacaoException("Lista de pendência não encontrada ou inativa.");
        }

        listaPendenciaService.atualizar(listaPendenciaRepository.getReferenceById(dados.id()),dados);

        return ResponseEntity.ok().body(new DadosDetalhamentoListaPendencia(listaPendenciaRepository.getReferenceById(dados.id())));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<DadosResponse> deletar(@RequestBody @Valid DadosCancelamentoListaPendencia dados) {
        if (!listaPendenciaRepository.existsByIdAndAtivoTrue(dados.listaPendenciaId())) {
            throw new ValidacaoException("Lista de pendência não encontrada ou inativa.");
        }

        listaPendenciaService.cancelar(listaPendenciaRepository.getReferenceById(dados.listaPendenciaId()),dados);

        var dadosResponse = new DadosResponse(
                LocalDateTime.now(),
                "Sucesso",
                HttpStatus.OK.value(),
                "Lista de pendência cancelada com sucesso!"
        );

        return ResponseEntity.ok().body(dadosResponse);
    }

}
