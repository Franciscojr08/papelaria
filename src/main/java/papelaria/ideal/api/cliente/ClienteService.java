package papelaria.ideal.api.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import papelaria.ideal.api.aluno.Aluno;
import papelaria.ideal.api.endereco.Endereco;

import java.time.LocalDateTime;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public void cadastrar(DadosCadastroCliente dados) {
        validarIntegridade(dados.cpf());
        cadastrarCliente(dados);
    }

    private void validarIntegridade(String cpf) {
        if (clienteRepository.existsByCpf(cpf)) {
            throw new RuntimeException("Não foi possível cadastrar o cliente. O CPF informado já está cadastrado.");
        }
    }

    private void cadastrarCliente(DadosCadastroCliente dados) {
        var cliente = new Cliente();
        cliente.setNome(dados.nome());
        cliente.setCpf(dados.cpf().replaceAll("[.-]", ""));
        cliente.setTelefone(dados.telefone());
        cliente.setEmail(dados.email());
        cliente.setEndereco(new Endereco(dados.endereco()));
        cliente.setResponsavelAluno(dados.responsavelAluno());
        cliente.setAtivo(true);
        cliente.setDataCadastro(LocalDateTime.now());

        clienteRepository.save(cliente);
    }

    public void atualizarInformacoes(Cliente cliente, DadosAtualizacaoCliente dados) {
        if (dados.cpf() != null) {
            if (!cliente.getCpf().equals(dados.cpf().replaceAll("[.-]", "")) && clienteRepository.existsByCpf(dados.cpf())) {
                throw new RuntimeException("Não foi possível atualizar o cliente. O CPF informado já está cadastrado.");
            }

            cliente.setCpf(dados.cpf());
        }

        if (dados.nome() != null) {
            cliente.setNome(dados.nome());
        }

        if (dados.telefone() != null) {
            cliente.setTelefone(dados.telefone());
        }

        if (dados.email() != null) {
            cliente.setEmail(dados.email());
        }

        if (dados.endereco() != null) {
            cliente.getEndereco().atualizarInformacoes(dados.endereco());
        }

        if (dados.responsavelAluno() != null) {
            cliente.setResponsavelAluno(dados.responsavelAluno());
        }

        cliente.setDataAtualizacao(LocalDateTime.now());
    }

    public void deletar(Cliente cliente) {
        if (cliente.getAlunos().stream().anyMatch(Aluno::getAtivo)) {
            throw new RuntimeException(
                    "Não foi possível deletar o cliente, o mesmo é responsável por um ou mais alunos."
            );
        }

        cliente.setDataAtualizacao(LocalDateTime.now());
        cliente.setAtivo(false);
    }
}