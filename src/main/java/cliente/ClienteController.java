package cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;
    ClienteRepository repository;

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody Cliente cliente) {
        String cpf =cliente.getCpf();
        try {
            repository.findByCpf(cpf);
        } catch(Exception e) {
            throw new RuntimeException("CPF já cadastrado. Não é possível cadastrar o cliente.");
        }


        clienteService.cadastrar(cliente);
        return ResponseEntity.ok("Cliente cadastrado com sucesso.");
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<String> deletar(@PathVariable Long clienteId) {
        clienteService.deletar(clienteId);
        return ResponseEntity.ok("Cliente excluído com sucesso.");
    }
}