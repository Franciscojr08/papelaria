package papelaria.ideal.api.Turma;

import java.time.LocalDateTime;

public record DadosTurma(
		Long id,
		String nome,
		String serieNome,
		LocalDateTime dataCadastro,
		LocalDateTime dataAtualizacao,
		Boolean ativo
) {

	public DadosTurma(Turma turma) {
		this(
				turma.getId(),
				turma.getNome(),
				turma.getSerie().getNome(),
				turma.getDataCadastro(),
				turma.getDataAtualizacao(),
				turma.getAtivo()
		);
	}
}
