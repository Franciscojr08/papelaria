package listaPendencia;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroListaPendencia(
        @NotBlank
        Cliente cliente,
        @NotBlank
        int dataCadastro,

        int dataEntrega,

        @NotBlank
        String situacao,

        @NotBlank
        boolean entregue,

        @NotBlank
        Produto produto

) {

}
