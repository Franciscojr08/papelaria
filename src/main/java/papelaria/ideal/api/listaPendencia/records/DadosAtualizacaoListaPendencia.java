package papelaria.ideal.api.listaPendencia.records;

import jakarta.validation.constraints.NotNull;
import papelaria.ideal.api.listaPendencia.SituacaoListaPendenciaEnum;
import papelaria.ideal.api.pedido.records.DadosPedidoLivroKitLivro;

import java.time.LocalDateTime;
import java.util.List;

public record DadosAtualizacaoListaPendencia(
        @NotNull
        Long id,
        List<DadosPedidoLivroKitLivro> livros,
        List<DadosPedidoLivroKitLivro> kitLivros
) {
}
