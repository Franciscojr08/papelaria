package papelaria.ideal.api.Serie;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoSerie(
		@NotNull
		Long id,
		String nome,
		Boolean ativo
) {
}
