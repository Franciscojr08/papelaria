package listaPendencia;

public record DadosListagemListaPendencia(Long id, Pedido pedido, SituacaoListaPendenciaEnum situacao) {
    public DadosListagemListaPendencia(ListaPendencia listaPendencia){
        this(listaPendencia.getId(), listaPendencia.getPedido(), listaPendencia.getSituacao());
    }
}
