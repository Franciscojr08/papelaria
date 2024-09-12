package papelaria.ideal.api.Serie.records;

import papelaria.ideal.api.Serie.Serie;

public record DadosComboSerie(Long id, String value) {

	public DadosComboSerie(Serie serie) {
		this (
				serie.getId(),
				serie.getNome()
		);
	}
}
