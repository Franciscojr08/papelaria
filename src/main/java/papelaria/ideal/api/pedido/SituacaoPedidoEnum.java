package papelaria.ideal.api.pedido;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum SituacaoPedidoEnum {

	@Enumerated(EnumType.STRING)
	PENDENTE,
	@Enumerated(EnumType.STRING)
	FINALIZADO,
	@Enumerated(EnumType.STRING)
	CANCELADO
}
