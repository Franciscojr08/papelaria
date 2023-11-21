package aluno;

import cliente.Cliente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "turma_id")
    private Turma turma;


    public Aluno(String nome, String matricula, boolean retirada, String entrega, Cliente cliente, Turma turma) {
        this.nome = nome;
        this.matricula = matricula;
        this.retirada = retirada;
        this.entrega = entrega;
        this.cliente = cliente;
        this.turma = turma;
    }
}