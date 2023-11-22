package papelaria.ideal.api.listaPendencia;

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
        LocalDateTime dataCadastro,
        LocalDateTime dataEntrega,
        LocalDateTime dataAtualizacao,
        Boolean entregue,
        Boolean ativo,
        List<DadosListaPendenciaLivro> listaPendenciaLivros,
        List<DadosListaPendenciaKitLivro> listaPendenciaKitLivro
        ) {

    public DadosDetalhamentoListaPendencia(ListaPendencia listaPendencia) {
        this(
                listaPendencia.getId(),
                listaPendencia.getPedido().getId(),
                listaPendencia.getPedido().getCliente().getNome(),
                listaPendencia.getSituacao(),
                listaPendencia.getDataCadastro(),
                listaPendencia.getDataEntrega(),
                listaPendencia.getDataAtualizacao(),
                listaPendencia.getEntregue(),
                listaPendencia.getAtivo(),
                listaPendencia.getDadosListaPendenciaLivro(),
                listaPendencia.getDadosListaPendenciaKitLivro()
        );
    }
}
