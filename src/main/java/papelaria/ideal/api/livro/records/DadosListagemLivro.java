package papelaria.ideal.api.livro.records;

import papelaria.ideal.api.livro.Livro;

import java.time.LocalDateTime;

public record DadosListagemLivro(
		Long id,
		String identificador,
		String nome,
		String usoInterno,
		String serieNome,
		Float valor,
		Long quantidadeDisponivel,
		Long quantidadePedidos,
		Long quantidadePendencias,
		LocalDateTime dataCadastro,
		LocalDateTime dataAtualizacao
		) {

	public DadosListagemLivro(Livro livro) {
		this(
				livro.getId(),
				livro.getIdentificador(),
				livro.getNome(),
				livro.getDescricaoUsoInterno(),
				(livro.getSerie() != null) ? livro.getSerie().getNome() : null,
				livro.getValor(),
				livro.getQuantidadeDisponivel(),
				livro.getQuantidadePedidosAtivos(),
				livro.getQuantidadePendenciasAtivas(),
				livro.getDataCadastro(),
				livro.getDataAtualizacao()
		);
	}
}
