package aluno;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroAluno(
        @NotBlank(message = "{nome.obrigatorio}")
        String nome,

        @NotBlank(message = "{matricula.obrigatorio}")
        @Pattern(regexp = "\\d{12}", message = "{matricula.invalido}")
        String matricula


        ) {
}
