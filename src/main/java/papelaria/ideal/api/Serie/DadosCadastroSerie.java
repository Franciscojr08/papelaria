package papelaria.ideal.api.Serie;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroSerie(
		@NotBlank
		String nome
) {
}
