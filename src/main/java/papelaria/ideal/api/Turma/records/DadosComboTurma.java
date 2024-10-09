package papelaria.ideal.api.Turma.records;

import papelaria.ideal.api.Turma.Turma;

public record DadosComboTurma(Long id, String value) {

	public DadosComboTurma(Turma turma) {
		this(
				turma.getId(),
				turma.getNome()
		);
	}
}
