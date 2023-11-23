package papelaria.ideal.api.cliente;

import papelaria.ideal.api.aluno.Aluno;
import papelaria.ideal.api.aluno.DadosClienteAluno;
import papelaria.ideal.api.endereco.Endereco;

import java.time.LocalDateTime;
import java.util.List;

public record DadosDetalhamentoCliente(
        Long id,
        String nome,
        String cpf,
        String telefone,
        String email,
        Endereco endereco,
        LocalDateTime dataCadastro,
        LocalDateTime dataAtualizacao,
        List<DadosClienteAluno> dadosClienteAlunos
) {

    public DadosDetalhamentoCliente(Cliente cliente) {
        this(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getEmail(),
                cliente.getEndereco(),
                cliente.getDataCadastro(),
                cliente.getDataAtualizacao(),
                cliente.getDadosClienteAluno()
        );
    }
}
