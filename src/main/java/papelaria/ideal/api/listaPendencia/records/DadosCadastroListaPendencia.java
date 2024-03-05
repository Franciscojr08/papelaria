package papelaria.ideal.api.listaPendencia.records;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import papelaria.ideal.api.listaPendencia.SituacaoListaPendenciaEnum;

import java.time.LocalDateTime;
import java.util.List;

public record DadosCadastroListaPendencia(
        @NotNull
        Long pedidoId,
        @NotNull
        LocalDateTime dataCadastro,
        @NotNull
        @Enumerated(EnumType.STRING)
        SituacaoListaPendenciaEnum situacao,
        List<DadosCadastroPendenciaLivroKitLivro> livros,
        List<DadosCadastroPendenciaLivroKitLivro> kitLivros
) {
}
