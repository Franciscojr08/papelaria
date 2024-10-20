package papelaria.ideal.api.aluno.records;

import papelaria.ideal.api.aluno.Aluno;

import java.time.LocalDateTime;

public record DadosDetalhamentoAluno(
		Long id,
		String nome,
		String matricula,
		String rg,
		String cpf,
		Long turmaId,
		Long clienteResponsavelId,
		LocalDateTime dataCadastro,
		LocalDateTime dataAtualizacao
) {

	public DadosDetalhamentoAluno(Aluno aluno) {
		this(
				aluno.getId(),
				aluno.getNome(),
				aluno.getMatricula(),
				aluno.getRg(),
				aluno.getCpf(),
				aluno.getTurma().getId(),
				aluno.getCliente().getId(),
				aluno.getDataCadastro(),
				aluno.getDataAtualizacao()
		);
	}
}
