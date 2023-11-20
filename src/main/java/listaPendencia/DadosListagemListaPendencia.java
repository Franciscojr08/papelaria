package listaPendencia;

public record DadosListagemListaPendencia(Long id, Cliente cliente, String situacao) {
    public DadosListagemListaPendencia(ListaPendencia listaPendencia){
        this(listaPendencia.getId(), listaPendencia.getCliente(), listaPendencia.getSituacao());
    }
}
