package aluno;

import cliente.Cliente;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import turma.Turma;

public record DadosCadastroAluno(
        @NotBlank(message = "{nome.obrigatorio}")
        String nome,
        @Valid
        @NotBlank(message = "{matricula.obrigatorio}")
        @Pattern(regexp = "\\d{12}", message = "{matricula.invalido}")
        String matricula,
        @NotBlank(message = "{cliente.obrigatorio}")
        Cliente cliente,

        @NotBlank(message = "{turma.obrigatorio}")
        Turma turma

        ) {
}
