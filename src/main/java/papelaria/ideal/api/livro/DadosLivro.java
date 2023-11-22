package papelaria.ideal.api.livro;

public record DadosLivro(
		Long id,
		String identificador,
		String nome,
		Boolean usoInterno,
		Float valor,
		Long quantidadeDisponivel,
		String serieNome,
		Boolean ativo
) {
}
