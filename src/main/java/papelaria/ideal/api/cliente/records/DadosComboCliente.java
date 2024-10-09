package papelaria.ideal.api.cliente.records;

import papelaria.ideal.api.cliente.Cliente;

public record DadosComboCliente(Long id, String value) {

	public DadosComboCliente(Cliente cliente) {
		this(
				cliente.getId(),
				cliente.getNome()
		);
	}
}
