package listaPendencia;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoListaPendencia(
        @NotNull
        Long id,
        int dataEntrega,
        String situacao,

        boolean entregue

) {
}
