package papelaria.ideal.api.pedido;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import papelaria.ideal.api.utils.FormaPagamentoEnum;

import java.time.LocalDateTime;
import java.util.List;

public record DadosCadastroPedido(
		@NotNull(message = "{dataPedido.obrigatoria}")
		LocalDateTime dataPedido,
		@Positive(message = "{valorPedido.obrigatorio}")
		float valor,
		float desconto,
		@NotNull(message = "{situacaoPedido.obrigatoria}")
		@Enumerated(EnumType.STRING)
		SituacaoPedidoEnum situacaoPedido,
		@NotNull(message = "{formaPagamentoPedido.obrigatoria}")
		@Enumerated(EnumType.STRING)
		FormaPagamentoEnum formaPagamento,
		LocalDateTime dataEntrega,
		@NotNull(message = "{clientePedido.obrigatorio}")
		Long clienteId,
		List<DadosCadastroPedidoLivroKitLivro> livros,
		List<DadosCadastroPedidoLivroKitLivro> kitLivros
) {
}
