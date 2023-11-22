package cliente;

import aluno.Aluno;
import endereco.Endereco;

public record DadosDetalhamentoCliente(long id, String nome, String cpf, String telefone, Endereco endereco, Boolean responsavelAluno, Aluno aluno) {

    public DadosDetalhamentoCliente(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getCpf(), cliente.getTelefone(), cliente.getEndereco(), cliente.getResponsavelAluno(), cliente.getAluno());
    }
}
