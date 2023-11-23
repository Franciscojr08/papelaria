package papelaria.ideal.api.aluno;

import java.time.LocalDateTime;

public record DadosAluno(
		Long id,
		String nome,
		String matricula,
		String rg,
		String cpf,
		String turmaNome,
		String clienteNome,
		LocalDateTime dataCadastro,
		LocalDateTime dataAtualizacao,
		Boolean ativo
) {

	public DadosAluno(Aluno aluno) {
		this(
				aluno.getId(),
				aluno.getNome(),
				aluno.getMatricula(),
				aluno.getRg(),
				aluno.getCpf(),
				"turma",
				aluno.getCliente().getNome(),
				aluno.getDataCadastro(),
				aluno.getDataAtualizacao(),
				aluno.getAtivo()
		);
	}
}
