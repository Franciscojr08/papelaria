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
    private boolean retirada;
    private String entrega;
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;
    @Transient
    private Turma turma;

    public Aluno(String nome, String matricula, boolean retirada, String entrega, Cliente cliente, Turma turma) {
        this.nome = nome;
        this.matricula = matricula;
        this.retirada = retirada;
        this.entrega = entrega;
        this.cliente = cliente;
        this.turma = turma;
    }

    public static List<Aluno> listarTodos(AlunoRepository alunoRepository) {
        return null;
    }

    public static Aluno listarPorId(AlunoRepository alunoRepository, Long id) {
        return null;
    }

    public static List<Aluno> listarPorCliente(AlunoRepository alunoRepository, Long clienteId) {
        return null;
    }

    public void cadastrar(AlunoRepository alunoRepository) {
    }

    public void atualizar(AlunoRepository alunoRepository) {
    }

    public void deletar(AlunoRepository alunoRepository) {
    }
}