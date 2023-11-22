package papelaria.ideal.api.kitLivro;

import java.time.LocalDateTime;

public record DadosKitLivro(
		Long id,
		String nome,
		String descricao,
		Float valor,
		Long quantidadeDisponivel,
		LocalDateTime dataCadastro,
		LocalDateTime dataAtualizacao,
		Boolean Ativo
) {
}
