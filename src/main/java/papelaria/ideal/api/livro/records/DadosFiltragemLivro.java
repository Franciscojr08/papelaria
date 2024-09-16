package papelaria.ideal.api.livro.records;

public record DadosFiltragemLivro(
		String identificador,
		String nome,
		Long quantidadeDisponivel,
		Long serieId,
		Boolean usoInterno,
		Float valor
) {
}
