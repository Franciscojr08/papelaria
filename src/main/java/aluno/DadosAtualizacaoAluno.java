package aluno;

import cliente.Cliente;
import jakarta.validation.constraints.NotNull;
import turma.Turma;

public record DadosAtualizacaoAluno(
        @NotNull
        Long id,
        String nome,
        Cliente cliente,
        Turma turma){

}
