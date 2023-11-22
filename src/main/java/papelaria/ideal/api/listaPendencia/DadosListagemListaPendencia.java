package papelaria.ideal.api.listaPendencia;

import papelaria.ideal.api.pedido.Pedido;

public record DadosListagemListaPendencia(Long id, Pedido pedido, SituacaoListaPendenciaEnum situacao) {
    public DadosListagemListaPendencia(ListaPendencia listaPendencia){
        this(listaPendencia.getId(), listaPendencia.getPedido(), listaPendencia.getSituacao());
    }
}
