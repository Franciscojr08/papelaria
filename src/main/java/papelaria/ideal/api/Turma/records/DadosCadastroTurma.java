package papelaria.ideal.api.Turma.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroTurma(
		@NotBlank()
		String nome,
		@NotNull()
		Long serieId
) {
}
