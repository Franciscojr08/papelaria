package papelaria.ideal.api.listaPendencia;

import papelaria.ideal.api.listaPendencia.listaPendenciaKitLivro.ListaPendenciaKitLivro;
import papelaria.ideal.api.listaPendencia.listaPendenciaLivro.ListaPendenciaLivro;

import java.time.LocalDateTime;

public record DadosListagemListaPendencia(
        Long id,
        Long pedidoId,
        String nomeCliente,
        Long quantidadeLivros,
        Long quantidadeKitLivros,
        SituacaoListaPendenciaEnum situacao,
        LocalDateTime dataCadastro,
        Boolean entregue,
        Boolean ativo
) {

    public DadosListagemListaPendencia(ListaPendencia listaPendencia) {
        this(
                listaPendencia.getId(),
                listaPendencia.getPedido().getId(),
                listaPendencia.getPedido().getCliente().getNome(),
                listaPendencia.getListaPendenciaLivro().stream().mapToLong(ListaPendenciaLivro::getQuantidade).sum(),
                listaPendencia.getListaPendenciaKitLivro().stream().mapToLong(ListaPendenciaKitLivro::getQuantidade).sum(),
                listaPendencia.getSituacao(),
                listaPendencia.getDataCadastro(),
                listaPendencia.getEntregue(),
                listaPendencia.getAtivo()
        );
    }
}
