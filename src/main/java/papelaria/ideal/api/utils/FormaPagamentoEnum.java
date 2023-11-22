package papelaria.ideal.api.utils;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum FormaPagamentoEnum {

	@Enumerated(EnumType.STRING)
	CREDITO,
	@Enumerated(EnumType.STRING)
	DEBITO,
	@Enumerated(EnumType.STRING)
	PIX,
	@Enumerated(EnumType.STRING)
	DINHEIRO,
	@Enumerated(EnumType.STRING)
	TRANSFERENCIA
}
