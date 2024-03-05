package papelaria.ideal.api.pedido.livro;

public record DadosPedidoLivro(
		Long id,
		String identificador,
		String nome,
		Long quantidade,
		Long quantidadeEntregue,
		Float valorUnitario,
		Float valorTotal
) {
}
