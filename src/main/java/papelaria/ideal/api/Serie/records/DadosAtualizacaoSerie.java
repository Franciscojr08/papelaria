package papelaria.ideal.api.Serie.records;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoSerie(
		@NotNull
		Long id,
		String nome
) {
}
