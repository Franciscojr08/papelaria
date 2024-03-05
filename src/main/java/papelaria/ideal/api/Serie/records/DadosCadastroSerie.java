package papelaria.ideal.api.Serie.records;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroSerie(
		@NotBlank
		String nome
) {
}
