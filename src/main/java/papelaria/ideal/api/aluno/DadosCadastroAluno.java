package papelaria.ideal.api.aluno;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroAluno(
        @NotBlank
        String nome,
        String matricula,
        String rg,
        String cpf,
        Long turmaId,
        Long clienteResponsavelId
) {
}
