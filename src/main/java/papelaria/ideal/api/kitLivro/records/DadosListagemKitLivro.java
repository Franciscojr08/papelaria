package papelaria.ideal.api.kitLivro.records;

import papelaria.ideal.api.kitLivro.KitLivro;

import java.time.LocalDateTime;

public record DadosListagemKitLivro(
		Long id,
		String nome,
		Float valor,
		Long quantidadeDisponivel,
		Long quantidadePedidos,
		Long quantidadePendencias,
		LocalDateTime dataCadastro,
		LocalDateTime dataAtualizacao
) {

	public DadosListagemKitLivro(KitLivro kitLivro) {
		this(
				kitLivro.getId(),
				kitLivro.getNome(),
				kitLivro.getValor(),
				kitLivro.getQuantidadeDisponivel(),
				kitLivro.getQuantidadePedidosAtivos(),
				kitLivro.getQuantidadePendenciasAtivas(),
				kitLivro.getDataCadastro(),
				kitLivro.getDataAtualizacao()
				);
	}
}
