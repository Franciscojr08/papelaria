package papelaria.ideal.api.pedido.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import papelaria.ideal.api.errors.ValidacaoException;
import papelaria.ideal.api.livro.LivroRepository;
import papelaria.ideal.api.pedido.records.DadosCadastroPedido;
import papelaria.ideal.api.pedido.records.DadosPedidoLivroKitLivro;

@Component
public class ValidadorLivroAtivo implements ValidadorPedidoInterface {

	@Autowired
	private LivroRepository livroRepository;

	public void validar(DadosCadastroPedido dados) {
		if (dados.livros() == null) {
			return;
		}

		for (DadosPedidoLivroKitLivro livro : dados.livros()) {
			if (!livroRepository.existsByIdAndAtivoTrue(livro.id())) {
				throw new ValidacaoException(
						"O pedido não pôde ser cadastrado pois algum livro adicionado está inativo ou " +
						" não está cadastrado!"
				);
			}
		}
	}
}
