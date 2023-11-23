package cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente cadastrar(Cliente cliente) {
        try {
            return clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("CPF já cadastrado. Não é possível cadastrar o cliente.");
        }
    }

    public void deletar(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);

        if (cliente != null) {
            if (cliente.getAlunos() != null && !cliente.getAlunos().isEmpty()) {
                throw new RuntimeException("Este cliente é responsável por um ou mais alunos. Não é possível excluí-lo.");
            }

            clienteRepository.delete(cliente);
        } else {
            throw new RuntimeException("Cliente não encontrado");
        }
    }
}