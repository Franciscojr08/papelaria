package papelaria.ideal.api.Turma.records;

import papelaria.ideal.api.Turma.Turma;
import papelaria.ideal.api.livro.Livro;

import java.time.LocalDateTime;

public record DadosListagemTurma(
		Long id,
		String nome,
		String serieNome,
		Long quantidadeAlunos,
		LocalDateTime dataCadastro,
		LocalDateTime dataAtualizacao
		) {

	public DadosListagemTurma(Turma turma) {
		this(
				turma.getId(),
				turma.getNome(),
				turma.getSerie().getNome(),
				turma.getQuantidadeAlunosAtivos(),
				turma.getDataCadastro(),
				turma.getDataAtualizacao()
				);
	}
}
