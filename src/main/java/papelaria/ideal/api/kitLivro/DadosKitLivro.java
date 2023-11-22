package papelaria.ideal.api.kitLivro;

public record DadosKitLivro(
		Long id,
		String nome,
		String descricao,
		Float valor,
		Long quantidadeDisponivel,
		Boolean Ativo
) {
}
