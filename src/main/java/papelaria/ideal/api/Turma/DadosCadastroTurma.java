package papelaria.ideal.api.Turma;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroTurma(
		@NotBlank(message = "O nome da turma não pode estar em branco")
		String nome,
		@NotNull(message = "A série não pode estar em branco")
		Long serieId
) {
}
