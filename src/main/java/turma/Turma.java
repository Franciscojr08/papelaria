package turma;

import aluno.Aluno;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @OneToMany(mappedBy = "turma")
    private List<Aluno> alunos;
}
