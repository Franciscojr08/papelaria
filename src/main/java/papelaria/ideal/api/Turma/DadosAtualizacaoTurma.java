package papelaria.ideal.api.Turma;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoTurma(
		@NotNull
		Long id,
		String nome,
		Boolean ativo
) {
}
