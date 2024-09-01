package papelaria.ideal.api.listaPendencia.records;

import papelaria.ideal.api.listaPendencia.ListaPendencia;
import papelaria.ideal.api.listaPendencia.SituacaoListaPendenciaEnum;
import papelaria.ideal.api.listaPendencia.listaPendenciaKitLivro.ListaPendenciaKitLivro;
import papelaria.ideal.api.listaPendencia.listaPendenciaLivro.ListaPendenciaLivro;

import java.time.LocalDateTime;

public record DadosListagemListaPendencia(
        Long id,
        Long pedidoId,
        String nomeCliente,
        Long quantidadeLivros,
        Long quantidadeLivrosEntregues,
        Long quantidadeKitLivros,
        Long quantidadeKitLivrosEntregues,
        SituacaoListaPendenciaEnum situacao,
        LocalDateTime dataCadastro,
        LocalDateTime dataEntrega
) {

    public DadosListagemListaPendencia(ListaPendencia listaPendencia) {
        this(
                listaPendencia.getId(),
                listaPendencia.getPedido().getId(),
                listaPendencia.getPedido().getCliente().getNome(),
                listaPendencia.getQuantidadeLivrosByEntregueTrueOrFalse(false),
                listaPendencia.getQuantidadeLivrosByEntregueTrueOrFalse(true),
                listaPendencia.getQuantidadeKitLivrosByEntregueTrueOrFalse(false),
                listaPendencia.getQuantidadeKitLivrosByEntregueTrueOrFalse(true),
                listaPendencia.getSituacao(),
                listaPendencia.getDataCadastro(),
                listaPendencia.getDataEntrega()
        );
    }
}
