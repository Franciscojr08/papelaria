package papelaria.ideal.api.kitLivro;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoKitLivro(
		@NotNull
		Long id,
		String nome,
		String descricao,
		Float valor,
		Long quantidadeDisponivel
) {
}
