package papelaria.ideal.api.cliente.records;

import papelaria.ideal.api.aluno.records.DadosListagemAluno;
import papelaria.ideal.api.cliente.Cliente;
import papelaria.ideal.api.endereco.Endereco;
import papelaria.ideal.api.pedido.records.DadosListagemPedido;

import java.time.LocalDateTime;
import java.util.List;

public record DadosDetalhamentoCliente(
        Long id,
        String nome,
        String cpf,
        String telefone,
        String email,
        Boolean responsavelAluno,
        List<DadosListagemAluno> dadosListagemAluno,
        List<DadosListagemPedido> dadosListagemPedido,
        Endereco endereco,
        LocalDateTime dataCadastro,
        LocalDateTime dataAtualizacao
        ) {

    public DadosDetalhamentoCliente(Cliente cliente) {
        this(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getEmail(),
                cliente.getResponsavelAluno(),
                cliente.getDadosAlunos(),
                cliente.getDadosPedidos(),
                cliente.getEndereco(),
                cliente.getDataCadastro(),
                cliente.getDataAtualizacao()
        );
    }
}
