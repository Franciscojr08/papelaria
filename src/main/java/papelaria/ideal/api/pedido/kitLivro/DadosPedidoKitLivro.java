package papelaria.ideal.api.pedido.kitLivro;

public record DadosPedidoKitLivro(
		Long id,
		String nome,
		Long quantidade,
		Float valorUnitario,
		Float valorTotal
) {
}
