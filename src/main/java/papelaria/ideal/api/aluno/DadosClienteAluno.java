package papelaria.ideal.api.aluno;

import java.time.LocalDateTime;

public record DadosClienteAluno(
		Long id,
		String nome,
		String nomeTurma,
		LocalDateTime dataCadastro
) {

	public DadosClienteAluno(Aluno aluno) {
		this(aluno.getId(), aluno.getNome(), "turma",aluno.getDataCadastro());
	}
}
