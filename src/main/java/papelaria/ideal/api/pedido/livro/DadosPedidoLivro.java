package papelaria.ideal.api.pedido.livro;

public record DadosPedidoLivro(
		String identificador,
		String nome,
		Long quantidade,
		Float valorUnitario,
		Float valorTotal
) {
}
