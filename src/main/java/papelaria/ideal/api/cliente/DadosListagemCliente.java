package papelaria.ideal.api.cliente;

import java.time.LocalDateTime;

public record DadosListagemCliente(
        Long id,
        String nome,
        String cpf,
        String telefone,
        String email,
        Boolean responsavelAluno,
        Long quantidadeAlunos,
        LocalDateTime dataCadastro
) {
    public DadosListagemCliente(Cliente cliente) {
        this(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getEmail(),
                cliente.getResponsavelAluno(),
		        (long) cliente.getAlunos().size(),
                cliente.getDataCadastro()
        );
    }
}

