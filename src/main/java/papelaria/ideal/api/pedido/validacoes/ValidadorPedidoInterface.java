package papelaria.ideal.api.pedido.validacoes;

import papelaria.ideal.api.pedido.records.DadosCadastroPedido;

public interface ValidadorPedidoInterface {

	public void validar(DadosCadastroPedido dados);
}
