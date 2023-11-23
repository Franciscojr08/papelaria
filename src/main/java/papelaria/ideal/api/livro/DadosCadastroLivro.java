package papelaria.ideal.api.livro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroLivro(
		@NotBlank
		String identificador,
		@NotBlank
		String nome,
		@NotNull
		Boolean usoInterno,
		@NotNull
		Float valor,
		@NotNull
		Long quantidadeDisponivel,
		Long serieId
) {
}
