package papelaria.ideal.api.cliente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import papelaria.ideal.api.aluno.Aluno;
import papelaria.ideal.api.cliente.records.DadosAtualizacaoCliente;
import papelaria.ideal.api.cliente.records.DadosCadastroCliente;
import papelaria.ideal.api.cliente.records.DadosComboCliente;
import papelaria.ideal.api.cliente.records.DadosFiltragemCliente;
import papelaria.ideal.api.endereco.Endereco;
import papelaria.ideal.api.errors.ValidacaoException;
import papelaria.ideal.api.pedido.Pedido;
import papelaria.ideal.api.pedido.SituacaoPedidoEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public void cadastrar(DadosCadastroCliente dados) {
        validarIntegridade(dados.cpf());
        cadastrarCliente(dados);
    }

    private void validarIntegridade(String cpf) {
        if (clienteRepository.existsByAtivoTrueAndCpf(cpf)) {
            throw new ValidacaoException("Não foi possível cadastrar o cliente. O CPF informado já está cadastrado.");
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
            if (!Objects.equals(cliente.getCpf(), dados.cpf().replaceAll("[.-]", "")) &&
                    clienteRepository.existsByAtivoTrueAndCpf(dados.cpf())
            ) {
                throw new ValidacaoException("Não foi possível atualizar o cliente. O CPF informado já está cadastrado.");
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
            if (!dados.responsavelAluno() && cliente.getAlunos().stream().anyMatch(Aluno::getAtivo)) {
                throw new ValidacaoException(
                        "Não foi possível atualizar o cliente. O mesmo é responsável por aluno(s), este campo não" +
                        " pode ser alterado."
                );
            }

            cliente.setResponsavelAluno(dados.responsavelAluno());
        }

        cliente.setDataAtualizacao(LocalDateTime.now());
    }

    public void deletar(Cliente cliente) {
        if (cliente.getAlunos().stream().anyMatch(Aluno::getAtivo)) {
            throw new ValidacaoException(
                    "Não foi possível deletar o cliente, o mesmo é responsável por um ou mais alunos."
            );
        }

        if (cliente.getPedidos()
                .stream()
                .anyMatch(pedido -> pedido.getSituacaoPedido() == SituacaoPedidoEnum.PENDENTE)
        ) {
            throw new ValidacaoException(
                    "Não foi possível deletar o cliente, o mesmo possui pedidos pendentes."
            );
        }

        cliente.setDataAtualizacao(LocalDateTime.now());
        cliente.setAtivo(false);
    }

    public Page<Cliente> filtrar(DadosFiltragemCliente filtros, Pageable pageable) {
        var clienteQueryNative = new ClienteQueryNative(entityManager);
        return clienteQueryNative.filtrarClientes(filtros,pageable);
    }

	public List<DadosComboCliente> montarCombo() {
        var clientes = clienteRepository.findAllByAtivoTrue();
        return clientes.stream().map(DadosComboCliente::new).toList();
    }
}