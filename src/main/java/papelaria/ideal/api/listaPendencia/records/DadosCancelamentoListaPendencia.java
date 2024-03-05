package papelaria.ideal.api.listaPendencia.records;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoListaPendencia(
		@NotNull
		Long listaPendenciaId,
		@NotNull
		Boolean atualizarEstoque
) {
}
