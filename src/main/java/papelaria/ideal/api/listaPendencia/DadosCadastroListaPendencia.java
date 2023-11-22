package papelaria.ideal.api.listaPendencia;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record DadosCadastroListaPendencia(
        @NotNull
        Long pedidoId,
        @NotNull
        LocalDateTime dataCadastro,
        LocalDateTime dataEntrega,
        @NotNull
        @Enumerated(EnumType.STRING)
        SituacaoListaPendenciaEnum situacao,
        @NotNull
        Boolean entregue,
        List<DadosCadastroPendenciaLivroKitLivro> livros,
        List<DadosCadastroPendenciaLivroKitLivro> kitLivros

) {

}
