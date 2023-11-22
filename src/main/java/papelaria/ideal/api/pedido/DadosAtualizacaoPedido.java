package papelaria.ideal.api.pedido;

import jakarta.validation.constraints.NotNull;
import papelaria.ideal.api.utils.FormaPagamentoEnum;

import java.time.LocalDateTime;

public record DadosAtualizacaoPedido(
		@NotNull(message = "{pedidoId.obrigatorio}")
		Long id,
		LocalDateTime dataPedido,
		LocalDateTime dataEntrega,
		Float desconto,
		SituacaoPedidoEnum situacaoPedido,
		FormaPagamentoEnum formaPagamento
) {
}
