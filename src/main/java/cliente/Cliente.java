package cliente;

import aluno.Aluno;
import aluno.AlunoRepository;
import endereco.Endereco;
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
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;

    @Transient
    private Endereco endereco;
    private Boolean responsavelAluno;

    @ManyToOne(fetch = FetchType.LAZY)
    private Aluno aluno;


    public void deletar(ClienteRepository clienteRepository, AlunoRepository alunoRepository) {
        if (this.aluno != null) {
            throw new RuntimeException("Cliente é responsável por um aluno e não pode ser excluído.");
        }

        clienteRepository.delete(this);
    }
}