package papelaria.ideal.api.Turma.records;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoTurma(
		@NotNull
		Long id,
		String nome,
		@NotNull
		Long serieId
) {
}
