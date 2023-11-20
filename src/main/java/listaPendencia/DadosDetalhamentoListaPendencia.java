package listaPendencia;

public record DadosDetalhamentoListaPendencia(Long id, Cliente cliente, int dataCadastro, int dataEntrega, String situacao, boolean entregue, Produto produto) {
    public DadosDetalhamentoListaPendencia(ListaPendencia listaPendencia){
        this(listaPendencia.getId(), listaPendencia.getCliente(), listaPendencia.getDataCadastro(), listaPendencia.getDataEntrega(), listaPendencia.getSituacao(), listaPendencia.isEntregue(), listaPendencia.getProduto());
    }
}
