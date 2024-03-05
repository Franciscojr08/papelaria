package papelaria.ideal.api.aluno.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoAluno(
        @NotNull
        Long id,
        @NotBlank
        String nome,
        String matricula,
        String rg,
        String cpf,
        @NotNull
        Long turmaId,
        @NotNull
        Long clienteResponsavelId
){

}
