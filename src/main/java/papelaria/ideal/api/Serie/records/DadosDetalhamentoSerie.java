package papelaria.ideal.api.Serie.records;

import papelaria.ideal.api.Serie.Serie;
import papelaria.ideal.api.Turma.records.DadosListagemTurma;
import papelaria.ideal.api.livro.records.DadosListagemLivro;

import java.time.LocalDateTime;
import java.util.List;

public record DadosDetalhamentoSerie(
		Long id,
		String nome,
		List<DadosListagemTurma> turmas,
		List<DadosListagemLivro> livros,
		LocalDateTime dataCadastro,
		LocalDateTime dataAtualizacao
		) {

	public DadosDetalhamentoSerie(Serie serie) {
		this(
				serie.getId(),
				serie.getNome(),
				serie.getDadosTurma(),
				serie.getDadosLivro(),
				serie.getDataCadastro(),
				serie.getDataAtualizacao()
		);
	}
}
