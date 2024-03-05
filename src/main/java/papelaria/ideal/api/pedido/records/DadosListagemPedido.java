package papelaria.ideal.api.pedido.records;

import papelaria.ideal.api.listaPendencia.SituacaoListaPendenciaEnum;
import papelaria.ideal.api.pedido.Pedido;
import papelaria.ideal.api.pedido.SituacaoPedidoEnum;
import papelaria.ideal.api.pedido.kitLivro.PedidoKitLivro;
import papelaria.ideal.api.pedido.livro.PedidoLivro;
import papelaria.ideal.api.utils.FormaPagamentoEnum;

import java.time.LocalDateTime;
import java.util.List;

public record DadosListagemPedido(
		Long id,
		LocalDateTime dataPedido,
		String nome_cliente,
		Float valor,
		Float desconto,
		Float valorTotal,
		Boolean pendencia,
		Long quantidadeLivros,
		Long quantidadeLivrosEntregue,
		Long quantidadeKitLivros,
		Long quantidadeKitLivrosEntregue,
		FormaPagamentoEnum formaPagamento,
		SituacaoPedidoEnum situacao
) {

	public DadosListagemPedido(Pedido pedido) {
		this(
				pedido.getId(),
				pedido.getDataPedido(),
				pedido.getCliente().getNome(),
				pedido.getValor(),
				pedido.getDesconto(),
				pedido.getValorTotal(),
				pedido.hasPendenciaAtivaBySituacao(SituacaoListaPendenciaEnum.PENDENTE),
				pedido.getPedidoLivro().stream().mapToLong(PedidoLivro::getQuantidade).sum(),
				pedido.getPedidoLivro().stream().mapToLong(PedidoLivro::getQuantidadeEntregue).sum(),
				pedido.getPedidoKitLivro().stream().mapToLong(PedidoKitLivro::getQuantidade).sum(),
				pedido.getPedidoKitLivro().stream().mapToLong(PedidoKitLivro::getQuantidadeEntregue).sum(),
				pedido.getFormaPagamento(),
				pedido.getSituacaoPedido()

		);
	}
}
