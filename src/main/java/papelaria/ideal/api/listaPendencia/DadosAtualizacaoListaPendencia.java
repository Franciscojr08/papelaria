package papelaria.ideal.api.listaPendencia;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAtualizacaoListaPendencia(
        @NotNull
        Long id,
        LocalDateTime dataEntrega,
        SituacaoListaPendenciaEnum situacao,
        Boolean entregue
) {
}
