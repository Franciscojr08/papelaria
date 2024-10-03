package papelaria.ideal.api.cliente.records;

import papelaria.ideal.api.aluno.Aluno;
import papelaria.ideal.api.cliente.Cliente;
import papelaria.ideal.api.pedido.Pedido;

import java.time.LocalDateTime;

public record DadosListagemCliente(
        Long id,
        String nome,
        String cpf,
        String telefone,
        String email,
        String responsavelAluno,
        Long quantidadeAlunos,
        Long quantidadePedidos,
        LocalDateTime dataCadastro,
        LocalDateTime dataAtualizacao
        ) {
    public DadosListagemCliente(Cliente cliente) {
        this(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getEmail(),
		        cliente.getResponsavelAluno() ? "Sim" : "Não",
		        cliente.getAlunos().stream().filter(Aluno::getAtivo).count(),
		        cliente.getPedidos().stream().filter(Pedido::getAtivo).count(),
		        cliente.getDataCadastro(),
		        cliente.getDataAtualizacao()
		);
    }
}

