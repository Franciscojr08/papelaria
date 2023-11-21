package papelaria.ideal.api.pedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import papelaria.ideal.api.cliente.Cliente;
import papelaria.ideal.api.cliente.ClienteRepository;
import papelaria.ideal.api.errors.ValidacaoException;
import papelaria.ideal.api.kitLivro.KitLivroRepository;
import papelaria.ideal.api.kitLivro.KitLivroService;
import papelaria.ideal.api.listaPendencia.DadosCadastroListaPendencia;
import papelaria.ideal.api.listaPendencia.ListaPendenciaService;
import papelaria.ideal.api.listaPendencia.SituacaoListaPendenciaEnum;
import papelaria.ideal.api.livro.LivroRepository;
import papelaria.ideal.api.livro.LivroService;
import papelaria.ideal.api.livroKitLivro.LivroKitLivroServiceInterface;
import papelaria.ideal.api.pedido.validacoes.ValidadorPedidoInterface;
import papelaria.ideal.api.livroKitLivro.DadosCadastroPedidoPedenciaLivroKitLivro;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private LivroRepository livroRepository;
	@Autowired
	private KitLivroRepository kitLivroRepository;
	@Autowired
	private List<ValidadorPedidoInterface> validadorPedido;
	@Autowired
	private ListaPendenciaService listaPendenciaService;
	@Autowired
	private LivroService livroService;
	@Autowired
	private KitLivroService kitLivroService;
	private final List<DadosCadastroPedidoPedenciaLivroKitLivro> listaPendenciaLivro = new ArrayList<>();
	private final List<DadosCadastroPedidoPedenciaLivroKitLivro> listaPendenciaKitLivro = new ArrayList<>();

	public void cadastrar(DadosCadastroPedido dados) {
		validarIntegridadePedido(dados);

		validadorPedido.forEach(validador -> validador.validar(dados));

		var cliente = clienteRepository.getReferenceById(dados.clienteId());
		var pedido = cadastrarPedido(dados, cliente);

		cadastrarPedidoLivro(dados,pedido);
		cadastrarPedidoKitLivro(dados,pedido);
		verificarNecessidadeCadastroListaPendencia(pedido);
	}

	private void validarIntegridadePedido(DadosCadastroPedido dados) {
		if (!clienteRepository.existsById(dados.clienteId())) {
			throw new ValidacaoException("O cliente informado é inválido ou não está cadastrado.");
		}

		if (dados.livros().isEmpty() && dados.kitLivros().isEmpty()) {
			throw new ValidacaoException(
				"Não é possível cadastrar um pedido sem livro ou kit de livro. " +
				"Adicione ao menos um livro ou kit de livro ao pedido."
			);
		}
	}

	private Pedido cadastrarPedido(DadosCadastroPedido dados, Cliente cliente) {
		var pedido = new Pedido(
				null,
				dados.dataPedido(),
				dados.valor(),
				dados.desconto(),
				dados.formaPagamento(),
				dados.situacaoPedido(),
				dados.dataEntrega(),
				cliente,
				true
		);

		return pedidoRepository.save(pedido);
	}

	private void cadastrarPedidoLivro(DadosCadastroPedido dados, Pedido pedido) {
		if (dados.livros().isEmpty()) {
			return;
		}

		for (DadosCadastroPedidoPedenciaLivroKitLivro dadosLivro : dados.livros()) {
			var livro = livroRepository.getReferenceById(dadosLivro.id());

			if (livro.getQuantidade() < dadosLivro.quantidade()) {
				var diffQuantidade = dadosLivro.quantidade() - livro.getQuantidade();
				var pendenciaLivro = new DadosCadastroPedidoPedenciaLivroKitLivro(livro.getId(),diffQuantidade);

				listaPendenciaLivro.add(pendenciaLivro);
				livroService.atualizarQuantidade(0L);
			} else {
				var quantidade = livro.getQuantidade() - dadosLivro.quantidade();
				livroService.atualizarQuantidade(quantidade);
			}

			pedidoRepository.savePedidoLivro(dadosLivro.quantidade(), pedido.getId(), livro.getId());
		}
	}

	private void cadastrarPedidoKitLivro(DadosCadastroPedido dados, Pedido pedido) {
		if (dados.kitLivros().isEmpty()) {
			return;
		}

		for (DadosCadastroPedidoPedenciaLivroKitLivro dadosKitLivro : dados.kitLivros()) {
			var kitLivro = kitLivroRepository.getReferenceById(dadosKitLivro.id());

			if (kitLivro.getQuantidade() < dadosKitLivro.quantidade()) {
				var diffQuantidade = dadosKitLivro.quantidade() - kitLivro.getQuantidade();
				var pendenciaKitLivro = new DadosCadastroPedidoPedenciaLivroKitLivro(kitLivro.getId(),diffQuantidade);

				listaPendenciaKitLivro.add(pendenciaKitLivro);
				kitLivroService.atualizarQuantidade(0L);
			} else {
				var quantidade = kitLivro.getQuantidade() - dadosKitLivro.quantidade();
				kitLivroService.atualizarQuantidade(quantidade);
			}

			pedidoRepository.savePedidoKitLivro(dadosKitLivro.quantidade(), pedido.getId(), kitLivro.getId());
		}
	}

	private void verificarNecessidadeCadastroListaPendencia(Pedido pedido) {
		if (listaPendenciaLivro.isEmpty() && listaPendenciaKitLivro.isEmpty()) {
			return;
		}

		var dadosCadastroListaPendencia = new DadosCadastroListaPendencia(
				pedido.getId(),
				pedido.getDataPedido(),
				null,
				SituacaoListaPendenciaEnum.PENDENTE,
				false,
				listaPendenciaLivro,
				listaPendenciaKitLivro
		);

		listaPendenciaService.cadastrar(dadosCadastroListaPendencia);
	}
}
