package papelaria.ideal.api.Serie;

import java.time.LocalDateTime;

public record DadosSerie(
		Long id,
		String nome,
		LocalDateTime dataCadastro,
		LocalDateTime dataAtualizacao,
		Boolean ativo
) {

	public DadosSerie(Serie serie) {
		this(serie.getId(), serie.getNome(), serie.getDataCadastro(),serie.getDataAtualizacao(),serie.getAtivo());
	}
}
