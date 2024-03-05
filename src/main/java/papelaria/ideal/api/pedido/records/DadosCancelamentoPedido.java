package papelaria.ideal.api.pedido.records;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoPedido(
		@NotNull
		Long pedidoId,
		@NotNull
		Boolean atualizarEstoque
) {
}
