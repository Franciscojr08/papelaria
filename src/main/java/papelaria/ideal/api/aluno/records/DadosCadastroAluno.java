package papelaria.ideal.api.aluno.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroAluno(
        @NotBlank
        String nome,
        String matricula,
        String rg,
        String cpf,
        @NotNull
        Long turmaId,
        @NotNull
        Long clienteResponsavelId
) {
}
