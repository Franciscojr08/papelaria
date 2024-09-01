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
		String nomeCliente,
		Float valor,
		Float desconto,
		Float valorTotal,
		String pendencia,
		Long quantidadeLivros,
		Long quantidadeKitLivros,
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
				pedido.hasPendenciaAtivaBySituacao(SituacaoListaPendenciaEnum.PENDENTE) ?
						String.valueOf(pedido.getTotalPendencias()) :
						"Não",
				pedido.getPedidoLivro().stream().mapToLong(PedidoLivro::getQuantidade).sum(),
				pedido.getPedidoKitLivro().stream().mapToLong(PedidoKitLivro::getQuantidade).sum(),
				pedido.getFormaPagamento(),
				pedido.getSituacaoPedido()
		);
	}
}
