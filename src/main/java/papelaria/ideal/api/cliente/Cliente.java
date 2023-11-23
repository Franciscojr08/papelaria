package papelaria.ideal.api.cliente;

import papelaria.ideal.api.aluno.Aluno;
import papelaria.ideal.api.aluno.DadosClienteAluno;
import papelaria.ideal.api.endereco.Endereco;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "papelaria/ideal/api/cliente")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Aluno> alunos;

    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private Boolean responsavelAluno;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
    private Boolean ativo;

    @Embedded
    private Endereco endereco;

    public List<DadosClienteAluno> getDadosClienteAluno() {
        if (this.alunos == null) {
            return  new ArrayList<>();
        }

        List<DadosClienteAluno> dadosClienteAlunoList = new ArrayList<>();

        for (Aluno aluno : this.alunos) {
            var dadosClienteAluno = new DadosClienteAluno(aluno);
            dadosClienteAlunoList.add(dadosClienteAluno);
        }

        return dadosClienteAlunoList;
    }
}