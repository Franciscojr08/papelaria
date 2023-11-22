package papelaria.ideal.api.pedido;

import papelaria.ideal.api.pedido.kitLivro.DadosPedidoKitLivro;
import papelaria.ideal.api.pedido.livro.DadosPedidoLivro;
import papelaria.ideal.api.utils.FormaPagamentoEnum;

import java.time.LocalDateTime;
import java.util.List;

public record DadosPedido(
		Long id,
		LocalDateTime dataPedido,
		LocalDateTime dataEntrega,
		LocalDateTime dataAtualizacao,
		String nomeCliente,
		Float valor,
		Float desconto,
		Float valorTotal,
		SituacaoPedidoEnum situacao,
		FormaPagamentoEnum formaPagamento,
		Boolean ativo,
		List<DadosPedidoLivro> livros,
		List<DadosPedidoKitLivro> kitLivros
) {

	public DadosPedido(Pedido pedido) {
		this(
				pedido.getId(),
				pedido.getDataPedido(),
				pedido.getDataEntrega(),
				pedido.getDataAtualizacao(),
				pedido.getCliente().getNome(),
				pedido.getValor(),
				pedido.getDesconto(),
				pedido.getValorTotal(),
				pedido.getSituacaoPedido(),
				pedido.getFormaPagamento(),
				pedido.getAtivo(),
				pedido.getDadosPedidoLivro(),
				pedido.getDadosPedidoKitLivro()
		);
	}
}
