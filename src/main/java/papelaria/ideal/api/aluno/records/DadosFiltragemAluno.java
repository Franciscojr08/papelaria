package papelaria.ideal.api.aluno.records;

public record DadosFiltragemAluno(
		String nome,
		String matricula,
		String cpf,
		String rg,
		Long clienteId,
		Long turmaId
) {
}
