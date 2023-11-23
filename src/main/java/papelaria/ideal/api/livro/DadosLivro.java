package papelaria.ideal.api.livro;

import java.time.LocalDateTime;

public record DadosLivro(
		Long id,
		String identificador,
		String nome,
		Boolean usoInterno,
		Float valor,
		Long quantidadeDisponivel,
		String serieNome,
		LocalDateTime dataCadastro,
		LocalDateTime dataAtualizacao,
		Boolean ativo
) {

	public DadosLivro(Livro livro) {
		this(
				livro.getId(),
				livro.getIdentificador(),
				livro.getNome(),
				livro.getUsoInterno(),
				livro.getValor(),
				livro.getQuantidadeDisponivel(),
				(livro.getSerie() != null) ? livro.getSerie().getNome() : null,
				livro.getDataCadastro(),
				livro.getDataAtualizacao(),
				livro.getAtivo()
		);
	}
}
