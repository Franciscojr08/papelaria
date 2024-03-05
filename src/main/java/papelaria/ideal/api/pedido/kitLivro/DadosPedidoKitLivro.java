package papelaria.ideal.api.pedido.kitLivro;

public record DadosPedidoKitLivro(
		Long id,
		String nome,
		Long quantidade,
		Long quantidadeEntregue,
		Float valorUnitario,
		Float valorTotal
) {
}
