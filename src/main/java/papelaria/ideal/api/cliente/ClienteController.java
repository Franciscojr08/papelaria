package papelaria.ideal.api.cliente;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping()
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid DadosCadastroCliente dados) {
        clienteService.cadastrar(dados);

        return ResponseEntity.ok("Cliente cadastrado com sucesso.");
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemCliente>> listar(Pageable paginacao) {
        var page = clienteRepository.findAllByAtivoTrue(paginacao).map(DadosListagemCliente::new);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoCliente> detalhar(@PathVariable Long id) {
        var cliente = clienteRepository.getReferenceById(id);

        return ResponseEntity.ok().body(new DadosDetalhamentoCliente(cliente));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoCliente> atualizar(@RequestBody @Valid DadosAtualizacaoCliente dados) {
        var cliente = clienteRepository.getReferenceById(dados.id());
        clienteService.atualizarInformacoes(cliente, dados);

        return ResponseEntity.ok().body(new DadosDetalhamentoCliente(cliente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id) {
        var cliente = clienteRepository.getReferenceById(id);
        clienteService.deletar(cliente);

        return ResponseEntity.noContent().build();
    }
}