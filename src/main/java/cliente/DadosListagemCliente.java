package cliente;

public record DadosListagemCliente(Long id, String nome, String cpf, String telefone) {
    public DadosListagemCliente(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getCpf(), cliente.getTelefone());
    }
}

