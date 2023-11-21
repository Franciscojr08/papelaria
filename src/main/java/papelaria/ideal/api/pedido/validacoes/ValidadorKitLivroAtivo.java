package papelaria.ideal.api.pedido.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import papelaria.ideal.api.errors.ValidacaoException;
import papelaria.ideal.api.kitLivro.KitLivroRepository;
import papelaria.ideal.api.pedido.DadosCadastroPedido;
import papelaria.ideal.api.livroKitLivro.DadosCadastroPedidoPedenciaLivroKitLivro;

@Component
public class ValidadorKitLivroAtivo implements ValidadorPedidoInterface {

	@Autowired
	private KitLivroRepository kitLivroRepository;

	public void validar(DadosCadastroPedido dados) {
		if (dados.kitLivros() == null) {
			return;
		}

		for (DadosCadastroPedidoPedenciaLivroKitLivro livro : dados.kitLivros()) {
			if (!kitLivroRepository.existsById(livro.id())) {
				throw new ValidacaoException(
						"O pedido não pôde ser cadastrado pois algum kit de livro adicionado não está cadastrado!"
				);
			}
		}


		for (DadosCadastroPedidoPedenciaLivroKitLivro livro : dados.kitLivros()) {
			if (kitLivroRepository.existsByIdAndAtivoFalse(livro.id())) {
				throw new ValidacaoException(
						"O pedido não pôde ser cadastrado pois algum kit de livro adicionado está inativo!"
				);
			}
		}
	}
}
