package papelaria.ideal.api.listaPendencia;

import java.time.LocalDateTime;
import java.util.List;

public record DadosDetalhamentoListaPendencia(Long id, Pedido pedido, LocalDateTime dataCadastro, LocalDateTime dataEntrega, SituacaoListaPendenciaEnum situacao, boolean entregue, List<DadosCadastroProduto> produto) {
    public DadosDetalhamentoListaPendencia(ListaPendencia listaPendencia){
        this(listaPendencia.getId(), listaPendencia.getPedido(), listaPendencia.getDataCadastro(), listaPendencia.getDataEntrega(), listaPendencia.getSituacao(), listaPendencia.isEntregue(), listaPendencia.getProduto());
    }
}
