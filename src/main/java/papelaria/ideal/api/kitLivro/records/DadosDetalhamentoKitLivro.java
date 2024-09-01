package papelaria.ideal.api.kitLivro.records;

import papelaria.ideal.api.kitLivro.KitLivro;
import papelaria.ideal.api.listaPendencia.records.DadosListagemListaPendencia;
import papelaria.ideal.api.pedido.records.DadosListagemPedido;

import java.time.LocalDateTime;
import java.util.List;

public record DadosDetalhamentoKitLivro(
		Long id,
		String nome,
		String descricao,
		Float valor,
		Long quantidadeDisponivel,
		LocalDateTime dataCadastro,
		LocalDateTime dataAtualizacao
) {
	public DadosDetalhamentoKitLivro(KitLivro kitLivro) {
		this(
				kitLivro.getId(),
				kitLivro.getNome(),
				kitLivro.getDescricao(),
				kitLivro.getValor(),
				kitLivro.getQuantidadeDisponivel(),
				kitLivro.getDataCadastro(),
				kitLivro.getDataAtualizacao()
		);
	}
}
