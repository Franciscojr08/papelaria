package papelaria.ideal.api.cliente;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import papelaria.ideal.api.cliente.records.*;
import papelaria.ideal.api.errors.DadosResponse;
import papelaria.ideal.api.errors.ValidacaoException;

import java.time.LocalDateTime;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping()
    @Transactional
    public ResponseEntity<DadosResponse> cadastrar(@RequestBody @Valid DadosCadastroCliente dados) {
        clienteService.cadastrar(dados);
        var dadosResponse = new DadosResponse(
                LocalDateTime.now(),
                "Sucesso",
                HttpStatus.OK.value(),
                "Cliente cadastrado com sucesso!"
        );

        return ResponseEntity.ok().body(dadosResponse);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemCliente>> listar(Pageable paginacao) {
        var page = clienteRepository.findAllByAtivoTrue(paginacao).map(DadosListagemCliente::new);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoCliente> detalhar(@PathVariable Long id) {
        if (!clienteRepository.existsByIdAndAtivoTrue(id)) {
            throw new ValidacaoException("Cliente não encontrado ou inativo.");
        }

        return ResponseEntity.ok().body(new DadosDetalhamentoCliente(clienteRepository.getReferenceById(id)));
    }

    @GetMapping("/filtrar")
    public ResponseEntity<Page<DadosListagemCliente>> filtrar(
            Pageable pageable,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) Boolean responsavel
    ) {
        var filtros = new DadosFiltragemCliente(nome, email, cpf, responsavel);
        var page = clienteService.filtrar(filtros,pageable).map(DadosListagemCliente::new);

        return ResponseEntity.ok().body(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoCliente> atualizar(@RequestBody @Valid DadosAtualizacaoCliente dados) {
        if (!clienteRepository.existsByIdAndAtivoTrue(dados.id())) {
            throw new ValidacaoException("Cliente não encontrado ou inativo.");
        }

        clienteService.atualizarInformacoes(clienteRepository.getReferenceById(dados.id()), dados);

        return ResponseEntity.ok().body(new DadosDetalhamentoCliente(clienteRepository.getReferenceById(dados.id())));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosResponse> deletar(@PathVariable Long id) {
        if (!clienteRepository.existsByIdAndAtivoTrue(id)) {
            throw new ValidacaoException("Cliente não encontrado ou inativo.");
        }

        clienteService.deletar(clienteRepository.getReferenceById(id));

        var dadosResponse = new DadosResponse(
                LocalDateTime.now(),
                "Sucesso",
                HttpStatus.OK.value(),
                "Cliente deletado com sucesso!"
        );

        return ResponseEntity.ok().body(dadosResponse);
    }
}