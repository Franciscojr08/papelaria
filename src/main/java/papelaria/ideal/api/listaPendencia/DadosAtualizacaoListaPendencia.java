package papelaria.ideal.api.listaPendencia;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record DadosAtualizacaoListaPendencia(
        @NotNull
        Long id,
        LocalDateTime dataEntrega,
        SituacaoListaPendenciaEnum situacao,

        boolean entregue,

        List<DadosCadastroLivroKitLivro> livros,
        List<DadosCadastroLivroKitLivro> kitLivros

) {
}
