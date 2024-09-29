package papelaria.ideal.api.livro.records;

import papelaria.ideal.api.listaPendencia.records.DadosListagemListaPendencia;
import papelaria.ideal.api.livro.Livro;
import papelaria.ideal.api.pedido.records.DadosListagemPedido;

import java.time.LocalDateTime;
import java.util.List;

public record DadosDetalhamentoLivro(
		Long id,
		String identificador,
		String nome,
		Boolean usoInterno,
		Long serieId,
		Float valor,
		Long quantidadeDisponivel,
		List<DadosListagemPedido> pedidos,
		List<DadosListagemListaPendencia> pendencias,
		LocalDateTime dataCadastro,
		LocalDateTime dataAtualizacao
		) {
	public DadosDetalhamentoLivro(Livro livro) {
		this(
				livro.getId(),
				livro.getIdentificador(),
				livro.getNome(),
				livro.getUsoInterno(),
				(livro.getSerie() != null) ? livro.getSerie().getId() : null,
				livro.getValor(),
				livro.getQuantidadeDisponivel(),
				livro.getDadosPedido(),
				livro.getDadosListaPendencia(),
				livro.getDataCadastro(),
				livro.getDataAtualizacao()
		);
	}
}
