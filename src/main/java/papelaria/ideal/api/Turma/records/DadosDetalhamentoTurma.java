package papelaria.ideal.api.Turma.records;

import papelaria.ideal.api.Turma.Turma;
import papelaria.ideal.api.aluno.records.DadosListagemAluno;

import java.time.LocalDateTime;
import java.util.List;

public record DadosDetalhamentoTurma(
		Long id,
		String nome,
		Long serieId,
		List<DadosListagemAluno> alunos,
		LocalDateTime dataCadastro,
		LocalDateTime dataAtualizacao
		) {

	public DadosDetalhamentoTurma(Turma turma) {
		this(
				turma.getId(),
				turma.getNome(),
				turma.getSerie().getId(),
				turma.getDadosAlunos(),
				turma.getDataCadastro(),
				turma.getDataAtualizacao()
		);
	}
}
