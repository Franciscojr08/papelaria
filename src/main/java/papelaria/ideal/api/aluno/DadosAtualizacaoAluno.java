package papelaria.ideal.api.aluno;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoAluno(
        @NotNull
        Long id,
        String nome,
        String matricula,
        String rg,
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")
        String cpf,
        Long turmaId,
        Long clienteResponsavelId
){

}
