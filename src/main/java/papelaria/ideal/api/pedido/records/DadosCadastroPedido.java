package papelaria.ideal.api.pedido.records;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import papelaria.ideal.api.pedido.SituacaoPedidoEnum;
import papelaria.ideal.api.utils.FormaPagamentoEnum;

import java.time.LocalDateTime;
import java.util.List;

public record DadosCadastroPedido(
		@NotNull(message = "{dataPedido.obrigatoria}")
		LocalDateTime dataPedido,
		LocalDateTime dataEntrega,
		float desconto,
		@NotNull(message = "{formaPagamentoPedido.obrigatoria}")
		@Enumerated(EnumType.STRING)
		FormaPagamentoEnum formaPagamento,
		@NotNull(message = "{clientePedido.obrigatorio}")
		Long clienteId,
		List<DadosPedidoLivroKitLivro> livros,
		List<DadosPedidoLivroKitLivro> kitLivros
) {
}
