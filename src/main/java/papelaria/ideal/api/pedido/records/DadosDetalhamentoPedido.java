package papelaria.ideal.api.pedido.records;

import papelaria.ideal.api.listaPendencia.SituacaoListaPendenciaEnum;
import papelaria.ideal.api.pedido.Pedido;
import papelaria.ideal.api.pedido.SituacaoPedidoEnum;
import papelaria.ideal.api.pedido.kitLivro.DadosPedidoKitLivro;
import papelaria.ideal.api.pedido.livro.DadosPedidoLivro;
import papelaria.ideal.api.utils.FormaPagamentoEnum;

import java.time.LocalDateTime;
import java.util.List;

public record DadosDetalhamentoPedido(
		Long id,
		String nomeCliente,
		Float valor,
		Float desconto,
		Float valorTotal,
		SituacaoPedidoEnum situacao,
		FormaPagamentoEnum formaPagamento,
		List<DadosPedidoLivro> livros,
		List<DadosPedidoKitLivro> kitLivros,
		Boolean pendencia,
		LocalDateTime dataPedido,
		LocalDateTime dataEntrega,
		LocalDateTime dataAtualizacao
		) {

	public DadosDetalhamentoPedido(Pedido pedido) {
		this(
				pedido.getId(),
				pedido.getCliente().getNome(),
				pedido.getValor(),
				pedido.getDesconto(),
				pedido.getValorTotal(),
				pedido.getSituacaoPedido(),
				pedido.getFormaPagamento(),
				pedido.getDadosPedidoLivro(),
				pedido.getDadosPedidoKitLivro(),
				pedido.hasPendenciaAtivaBySituacao(SituacaoListaPendenciaEnum.PENDENTE),
				pedido.getDataPedido(),
				pedido.getDataEntrega(),
				pedido.getDataAtualizacao()
		);
	}
}
