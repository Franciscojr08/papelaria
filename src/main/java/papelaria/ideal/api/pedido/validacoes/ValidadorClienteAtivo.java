package papelaria.ideal.api.pedido.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import papelaria.ideal.api.cliente.ClienteRepository;
import papelaria.ideal.api.errors.ValidacaoException;
import papelaria.ideal.api.pedido.records.DadosCadastroPedido;

@Component
public class ValidadorClienteAtivo implements ValidadorPedidoInterface {

	@Autowired
	private ClienteRepository clienteRepository;

	public void validar(DadosCadastroPedido dados) {
		if (dados.clienteId() == null) {
			throw new ValidacaoException("O cliente informado é inválido ou não está cadastrado.");
		}

		if (!clienteRepository.existsByIdAndAtivoTrue(dados.clienteId())) {
			throw new ValidacaoException("O pedido não pôde ser cadastrado pois o cliente informado está inativo!");
		}
	}
}
