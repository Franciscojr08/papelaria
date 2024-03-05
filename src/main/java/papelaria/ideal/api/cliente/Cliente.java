package papelaria.ideal.api.cliente;

import jakarta.validation.Valid;
import papelaria.ideal.api.aluno.Aluno;
import papelaria.ideal.api.aluno.records.DadosListagemAluno;
import papelaria.ideal.api.endereco.Endereco;
import jakarta.persistence.*;
import lombok.*;
import papelaria.ideal.api.pedido.records.DadosListagemPedido;
import papelaria.ideal.api.pedido.Pedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "cliente")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Aluno> alunos;

	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
	private List<Pedido> pedidos;

    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private Boolean responsavelAluno;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
    private Boolean ativo;

    @Embedded @Valid
    private Endereco endereco;

    public List<DadosListagemAluno> getDadosAlunos() {
        if (this.alunos == null) {
            return  new ArrayList<>();
        }

        List<DadosListagemAluno> dadosClienteAlunoList = new ArrayList<>();

        for (Aluno aluno : this.alunos) {
            if (!aluno.getAtivo()) {
                continue;
            }

            dadosClienteAlunoList.add(new DadosListagemAluno(aluno));
        }

        return dadosClienteAlunoList;
    }

    public List<DadosListagemPedido> getDadosPedidos() {
        if (this.pedidos == null) {
            return new ArrayList<>();
        }

        List<DadosListagemPedido> dadosListagemPedido = new ArrayList<>();

        for (Pedido pedido : this.pedidos) {
            if (!pedido.getAtivo()) {
                continue;
            }

            dadosListagemPedido.add(new DadosListagemPedido(pedido));
        }

        return dadosListagemPedido;
    }
}