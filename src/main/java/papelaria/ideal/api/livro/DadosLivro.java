package papelaria.ideal.api.livro;

import java.time.LocalDateTime;

public record DadosLivro(
		Long id,
		String identificador,
		String nome,
		Boolean usoInterno,
		Float valor,
		Long quantidadeDisponivel,
		String serieNome,
		LocalDateTime dataCadastro,
		LocalDateTime dataAtualizacao,
		Boolean ativo
) {
}
