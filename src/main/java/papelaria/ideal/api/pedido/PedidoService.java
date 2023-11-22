package papelaria.ideal.api.pedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import papelaria.ideal.api.cliente.Cliente;
import papelaria.ideal.api.cliente.ClienteRepository;
import papelaria.ideal.api.errors.ValidacaoException;
import papelaria.ideal.api.kitLivro.KitLivroRepository;
import papelaria.ideal.api.kitLivro.KitLivroService;
import papelaria.ideal.api.listaPendencia.DadosCadastroListaPendencia;
import papelaria.ideal.api.listaPendencia.DadosCadastroPendenciaLivroKitLivro;
import papelaria.ideal.api.listaPendencia.ListaPendenciaService;
import papelaria.ideal.api.listaPendencia.SituacaoListaPendenciaEnum;
import papelaria.ideal.api.livro.LivroRepository;
import papelaria.ideal.api.livro.LivroService;
import papelaria.ideal.api.pedido.kitLivro.PedidoKitLivro;
import papelaria.ideal.api.pedido.livro.PedidoLivro;
import papelaria.ideal.api.pedido.validacoes.ValidadorPedidoInterface;

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
	private final List<DadosCadastroPendenciaLivroKitLivro> listaPendenciaLivro = new ArrayList<>();
	private final List<DadosCadastroPendenciaLivroKitLivro> listaPendenciaKitLivro = new ArrayList<>();

	public void cadastrar(DadosCadastroPedido dados) {
		validarIntegridadePedido(dados);

		validadorPedido.forEach(validador -> validador.validar(dados));

		var cliente = clienteRepository.getReferenceById(dados.clienteId());
		var pedido = cadastrarPedido(dados, cliente);

		verificarNecessidadeCadastroListaPendencia(pedido);
	}

	private void validarIntegridadePedido(DadosCadastroPedido dados) {
		if (!clienteRepository.existsById(dados.clienteId())) {
			throw new ValidacaoException("O cliente informado é inválido ou não está cadastrado.");
		}

		if (dados.livros() == null && dados.kitLivros() == null) {
			throw new ValidacaoException(
				"Não é possível cadastrar um pedido sem livro ou kit de livro. " +
				"Adicione ao menos um livro ou kit de livro ao pedido."
			);
		}
	}

	private Pedido cadastrarPedido(DadosCadastroPedido dados, Cliente cliente) {
		var pedido = new Pedido(
				dados.dataPedido(),
				dados.valor(),
				dados.desconto(),
				dados.formaPagamento(),
				dados.situacaoPedido(),
				dados.dataEntrega(),
				cliente,
				true
		);

		var pedidoLivro = getPedidoLivro(dados,pedido);
		var pedidoKitLivro = getPedidoKitLivro(dados,pedido);

		if (!pedidoLivro.isEmpty()) {
			pedido.setPedidoLivro(pedidoLivro);
		}

		if (!pedidoKitLivro.isEmpty()) {
			pedido.setPedidoKitLivro(pedidoKitLivro);
		}

		return pedidoRepository.save(pedido);
	}

	private List<PedidoLivro> getPedidoLivro(DadosCadastroPedido dados, Pedido pedido) {
		if (dados.livros() == null) {
			return new ArrayList<>();
		}

		List<PedidoLivro> pedidoLivroList = new ArrayList<>();

		for (DadosCadastroPedidoLivroKitLivro dadosLivro : dados.livros()) {
			var livro = livroRepository.getReferenceById(dadosLivro.id());

			if (livro.getQuantidadeDisponivel() < dadosLivro.quantidadeSolicitada()) {
				var diffQuantidade = dadosLivro.quantidadeSolicitada() - livro.getQuantidadeDisponivel();
				var pendenciaLivro = new DadosCadastroPendenciaLivroKitLivro(livro.getId(),diffQuantidade);

				listaPendenciaLivro.add(pendenciaLivro);
				livroService.atualizarQuantidade(0L);
			} else {
				var quantidade = livro.getQuantidadeDisponivel() - dadosLivro.quantidadeSolicitada();
				livroService.atualizarQuantidade(quantidade);
			}

			var pedidoLivro = new PedidoLivro(
					null,
					pedido,
					livro,
					dadosLivro.quantidadeSolicitada(),
					dadosLivro.valorUnitario()
			);
			pedidoLivroList.add(pedidoLivro);
		}

		return pedidoLivroList;
	}

	private List<PedidoKitLivro> getPedidoKitLivro(DadosCadastroPedido dados, Pedido pedido) {
		if (dados.kitLivros() == null) {
			return new ArrayList<>();
		}

		List<PedidoKitLivro> pedidoKitLivroList = new ArrayList<>();

		for (DadosCadastroPedidoLivroKitLivro dadosKitLivro : dados.kitLivros()) {
			var kitLivro = kitLivroRepository.getReferenceById(dadosKitLivro.id());

			if (kitLivro.getQuantidadeDisponivel() < dadosKitLivro.quantidadeSolicitada()) {
				var diffQuantidade = dadosKitLivro.quantidadeSolicitada() - kitLivro.getQuantidadeDisponivel();
				var pendenciaKitLivro = new DadosCadastroPendenciaLivroKitLivro(kitLivro.getId(),diffQuantidade);

				listaPendenciaKitLivro.add(pendenciaKitLivro);
				kitLivroService.atualizarQuantidade(0L);
			} else {
				var quantidade = kitLivro.getQuantidadeDisponivel() - dadosKitLivro.quantidadeSolicitada();
				kitLivroService.atualizarQuantidade(quantidade);
			}

			var pedidoKitLivro = new PedidoKitLivro(
					null,
					pedido,
					kitLivro,
					dadosKitLivro.quantidadeSolicitada(),
					dadosKitLivro.valorUnitario()
			);
			pedidoKitLivroList.add(pedidoKitLivro);
		}

		return pedidoKitLivroList;
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
