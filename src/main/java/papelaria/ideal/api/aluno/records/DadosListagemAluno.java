package papelaria.ideal.api.aluno.records;

import papelaria.ideal.api.aluno.Aluno;

import java.time.LocalDateTime;

public record DadosListagemAluno(
		Long id,
		String nome,
		String matricula,
		String rg,
		String cpf,
		String turmaNome,
		String clienteNome,
		LocalDateTime dataCadastro,
		LocalDateTime dataAtualizacao
) {

	public DadosListagemAluno(Aluno aluno) {
		this(
				aluno.getId(),
				aluno.getNome(),
				aluno.getMatricula(),
				aluno.getRg(),
				aluno.getCpf(),
				aluno.getTurma().getNome(),
				aluno.getCliente().getNome(),
				aluno.getDataCadastro(),
				aluno.getDataAtualizacao()
		);
	}
}
