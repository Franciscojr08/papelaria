package papelaria.ideal.api.cliente.records;

public record DadosFiltragemCliente(
		String nome,
		String email,
		String cpf,
		Boolean responsavel
) {
}
