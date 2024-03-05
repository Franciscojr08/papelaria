package papelaria.ideal.api.listaPendencia.records;

import papelaria.ideal.api.listaPendencia.ListaPendencia;
import papelaria.ideal.api.listaPendencia.SituacaoListaPendenciaEnum;
import papelaria.ideal.api.listaPendencia.listaPendenciaKitLivro.DadosListaPendenciaKitLivro;
import papelaria.ideal.api.listaPendencia.listaPendenciaLivro.DadosListaPendenciaLivro;
import papelaria.ideal.api.pedido.Pedido;

import java.time.LocalDateTime;
import java.util.List;

public record DadosDetalhamentoListaPendencia(
        Long id,
        Long pedidoId,
        String nomeCliente,
        SituacaoListaPendenciaEnum situacao,
        Boolean ativo,
        List<DadosListaPendenciaLivro> listaPendenciaLivros,
        List<DadosListaPendenciaKitLivro> listaPendenciaKitLivro,
        LocalDateTime dataCadastro,
        LocalDateTime dataEntrega,
        LocalDateTime dataAtualizacao
        ) {

    public DadosDetalhamentoListaPendencia(ListaPendencia listaPendencia) {
        this(
                listaPendencia.getId(),
                listaPendencia.getPedido().getId(),
                listaPendencia.getPedido().getCliente().getNome(),
                listaPendencia.getSituacao(),
                listaPendencia.getAtivo(),
                listaPendencia.getDadosListaPendenciaLivro(),
                listaPendencia.getDadosListaPendenciaKitLivro(),
                listaPendencia.getDataCadastro(),
                listaPendencia.getDataEntrega(),
                listaPendencia.getDataAtualizacao()
        );
    }
}
