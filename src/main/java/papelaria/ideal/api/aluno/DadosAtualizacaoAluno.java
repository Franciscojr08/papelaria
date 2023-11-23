package papelaria.ideal.api.aluno;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoAluno(
        @NotNull
        Long id,
        String nome,
        String matricula,
        String rg,
        String cpf,
        Long turmaId,
        Long clienteResponsavelId
){

}
