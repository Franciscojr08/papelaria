package aluno;

import cliente.Cliente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import turma.Turma;

import java.util.List;

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
    private Cliente cliente;
    @Transient
    private Turma turma;

    public Aluno(String nome, String matricula, Cliente cliente, Turma turma) {
        this.nome = nome;
        this.matricula = matricula;
        this.cliente = cliente;
        this.turma = turma;
    }

    public void atualizar(AlunoRepository alunoRepository) {
    }

    public void deletar(AlunoRepository alunoRepository) {
    }
}