package papelaria.ideal.api.livro.records;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoLivro(
		@NotNull
		Long id,
		String identificador,
		String nome,
		Boolean usoInterno,
		Float valor,
		Long quantidadeDisponivel,
		Long serieId
) {
}
