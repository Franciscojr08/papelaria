package papelaria.ideal.api.Serie.records;

import papelaria.ideal.api.Serie.Serie;
import papelaria.ideal.api.Turma.Turma;

import java.time.LocalDateTime;

public record DadosListagemSerie(
		Long id,
		String nome,
		Long quantidadeTurmas,
		Long quantidadeLivros,
		LocalDateTime dataCadastro,
		LocalDateTime dataAtualizacao
		) {

	public DadosListagemSerie(Serie serie) {
		this(
				serie.getId(),
				serie.getNome(),
				serie.getQuantidadeTurmasAtivas(),
				serie.getQuantidadeLivrosAtivos(),
				serie.getDataCadastro(),
				serie.getDataAtualizacao()
		);
	}
}
