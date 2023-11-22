package papelaria.ideal.api.pedido;

import papelaria.ideal.api.pedido.kitLivro.PedidoKitLivro;
import papelaria.ideal.api.pedido.livro.PedidoLivro;

import java.util.List;

public record DadosListagemPedido(
		Long id,
		String nome_cliente,
		Long quantidade_livros,
		Long quantidade_kit_livros,
		Float valor,
		Float desconto,
		Float valor_total,
		SituacaoPedidoEnum situacao
) {

	public DadosListagemPedido(Pedido pedido) {
		this(
				pedido.getId(),
				pedido.getCliente().getNome(),
				pedido.getPedidoLivro().stream().mapToLong(PedidoLivro::getQuantidade).sum(),
				pedido.getPedidoKitLivro().stream().mapToLong(PedidoKitLivro::getQuantidade).sum(),
				pedido.getValor(),
				pedido.getDesconto(),
				pedido.getValorTotal(),
				pedido.getSituacaoPedido()
		);
	}
}
