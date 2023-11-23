package aluno;

import cliente.Cliente;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import turma.Turma;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String matricula;
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente; // nao seria clienteResponsavel(private Cliente responsavel)
    @Transient
    private Turma turma;
    private Boolean ativo;

    public Aluno(DadosCadastroAluno dados) {
        this.nome = dados.nome();
        this.matricula = dados.matricula();
        this.cliente = dados.cliente();
        this.turma = dados.turma();
        this.ativo = true;
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoAluno dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.cliente() != null) {
            this.cliente = dados.cliente();
        }
        if (dados.turma() != null) {
            this.turma = (dados.turma());
        }

    }

    public void deletar() {
        this.ativo = false;
    }

}