package papelaria.ideal.api.pedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import papelaria.ideal.api.cliente.Cliente;
import papelaria.ideal.api.cliente.ClienteRepository;
import papelaria.ideal.api.errors.ValidacaoException;
import papelaria.ideal.api.kitLivro.KitLivroRepository;
import papelaria.ideal.api.kitLivro.KitLivroService;
import papelaria.ideal.api.listaPendencia.records.DadosCadastroListaPendencia;
import papelaria.ideal.api.listaPendencia.records.DadosCadastroPendenciaLivroKitLivro;
import papelaria.ideal.api.listaPendencia.ListaPendenciaService;
import papelaria.ideal.api.listaPendencia.SituacaoListaPendenciaEnum;
import papelaria.ideal.api.livro.LivroRepository;
import papelaria.ideal.api.livro.LivroService;
import papelaria.ideal.api.pedido.kitLivro.PedidoKitLivro;
import papelaria.ideal.api.pedido.livro.PedidoLivro;
import papelaria.ideal.api.pedido.records.*;
import papelaria.ideal.api.pedido.validacoes.ValidadorPedidoInterface;

import java.time.LocalDateTime;
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

	public void cadastrar(DadosCadastroPedido dados) {
		validarIntegridadePedido(dados);

		validadorPedido.forEach(validador -> validador.validar(dados));

		List<DadosCadastroPendenciaLivroKitLivro> listaPendenciaLivro = new ArrayList<>();
		List<DadosCadastroPendenciaLivroKitLivro> listaPendenciaKitLivro = new ArrayList<>();

		var cliente = clienteRepository.getReferenceById(dados.clienteId());
		var pedido = cadastrarPedido(dados, cliente, listaPendenciaLivro, listaPendenciaKitLivro);

		verificarNecessidadeCadastroListaPendencia(pedido,listaPendenciaLivro,listaPendenciaKitLivro);
	}

	private void validarIntegridadePedido(DadosCadastroPedido dados) {
		if (!clienteRepository.existsByIdAndAtivoTrue(dados.clienteId())) {
			throw new ValidacaoException("O cliente informado é inválido ou não está cadastrado.");
		}

		if (dados.livros() == null && dados.kitLivros() == null) {
			throw new ValidacaoException(
				"Não é possível cadastrar um pedido sem livro ou kit de livro. " +
				"Adicione ao menos um livro ou kit de livro ao pedido."
			);
		}

		if (dados.dataEntrega() != null && dados.dataEntrega().isBefore(dados.dataPedido())) {
			throw new ValidacaoException("A data de entrega não pode ser inferior a data do pedido.");
		}

		if (dados.desconto() < 0) {
			throw new ValidacaoException("Não é possível aplicar um desconto negativo ao pedido.");
		}
	}

	private Pedido cadastrarPedido(
			DadosCadastroPedido dados,
			Cliente cliente,
			List<DadosCadastroPendenciaLivroKitLivro> listaPendenciaLivro,
			List<DadosCadastroPendenciaLivroKitLivro> listaPendenciaKitLivro
	) {
		var valor = 0.0F;
		var pedido = new Pedido(
				dados.dataPedido(),
				dados.desconto(),
				dados.formaPagamento(),
				SituacaoPedidoEnum.PENDENTE,
				cliente,
				true
		);

		var pedidoLivro = getPedidoLivro(dados,pedido,listaPendenciaLivro);
		var pedidoKitLivro = getPedidoKitLivro(dados,pedido,listaPendenciaKitLivro);

		if (!pedidoLivro.isEmpty()) {
			pedido.setPedidoLivro(pedidoLivro);
			valor += pedidoLivro
					.stream()
					.map(PedidoLivro::getValorTotalPedido)
					.reduce(0.0F,Float::sum);
		}

		if (!pedidoKitLivro.isEmpty()) {
			pedido.setPedidoKitLivro(pedidoKitLivro);
			valor += pedidoKitLivro
					.stream()
					.map(PedidoKitLivro::getValorTotalPedido)
					.reduce(0.0F,Float::sum);
		}

		pedido.setValor(valor);

		if (pedido.todosItensEntregues()) {
			pedido.setSituacaoPedido(SituacaoPedidoEnum.FINALIZADO);
			pedido.setDataEntrega(LocalDateTime.now());
		}

		return pedidoRepository.save(pedido);
	}

	private List<PedidoLivro> getPedidoLivro(
			DadosCadastroPedido dados,
			Pedido pedido,
			List<DadosCadastroPendenciaLivroKitLivro> listaPendenciaLivro
	) {
		if (dados.livros() == null) {
			return new ArrayList<>();
		}

		List<PedidoLivro> pedidoLivroList = new ArrayList<>();

		for (DadosPedidoLivroKitLivro dadosLivro : dados.livros()) {
			var quantidadeEntregue = 0L;
			var livro = livroRepository.getReferenceById(dadosLivro.id());

			if (livro.getQuantidadeDisponivel() < dadosLivro.quantidade()) {
				var diffQuantidade = dadosLivro.quantidade() - livro.getQuantidadeDisponivel();

				if (livro.getQuantidadeDisponivel() > 0 ) {
					quantidadeEntregue = livro.getQuantidadeDisponivel();
				}

				listaPendenciaLivro.add(new DadosCadastroPendenciaLivroKitLivro(livro.getId(),diffQuantidade));
				livroService.atualizarQuantidade(livro.getId(),0L);
			} else {
				var quantidadeDisponivelAtual = livro.getQuantidadeDisponivel() - dadosLivro.quantidade();
				quantidadeEntregue = dadosLivro.quantidade();
				livroService.atualizarQuantidade(livro.getId(),quantidadeDisponivelAtual);
			}

			pedidoLivroList.add(
					new PedidoLivro(
							null,
							pedido,
							livro,
							dadosLivro.quantidade(),
							quantidadeEntregue,
							livro.getValor()
					)
			);
		}

		return pedidoLivroList;
	}

	private List<PedidoKitLivro> getPedidoKitLivro(
			DadosCadastroPedido dados,
			Pedido pedido,
			List<DadosCadastroPendenciaLivroKitLivro> listaPendenciaKitLivro
	) {
		if (dados.kitLivros() == null) {
			return new ArrayList<>();
		}

		List<PedidoKitLivro> pedidoKitLivroList = new ArrayList<>();

		for (DadosPedidoLivroKitLivro dadosKitLivro : dados.kitLivros()) {
			var quantidadeEntregue = 0L;
			var kitLivro = kitLivroRepository.getReferenceById(dadosKitLivro.id());

			if (kitLivro.getQuantidadeDisponivel() < dadosKitLivro.quantidade()) {
				var diffQuantidade = dadosKitLivro.quantidade() - kitLivro.getQuantidadeDisponivel();

				if (kitLivro.getQuantidadeDisponivel() > 0) {
					quantidadeEntregue = kitLivro.getQuantidadeDisponivel();
				}

				listaPendenciaKitLivro.add(new DadosCadastroPendenciaLivroKitLivro(kitLivro.getId(),diffQuantidade));
				kitLivroService.atualizarQuantidade(kitLivro.getId(),0L);
			} else {
				var quantidadeDisponivelAtual = kitLivro.getQuantidadeDisponivel() - dadosKitLivro.quantidade();
				quantidadeEntregue = dadosKitLivro.quantidade();
				kitLivroService.atualizarQuantidade(kitLivro.getId(),quantidadeDisponivelAtual);
			}

			pedidoKitLivroList.add(
					new PedidoKitLivro(
							null,
							pedido,
							kitLivro,
							dadosKitLivro.quantidade(),
							quantidadeEntregue,
							kitLivro.getValor()
					)
			);
		}

		return pedidoKitLivroList;
	}

	private void verificarNecessidadeCadastroListaPendencia(
			Pedido pedido,
			List<DadosCadastroPendenciaLivroKitLivro> listaPendenciaLivro,
			List<DadosCadastroPendenciaLivroKitLivro> listaPendenciaKitLivro
	) {
		if (listaPendenciaLivro.isEmpty() && listaPendenciaKitLivro.isEmpty()) {
			return;
		}

		listaPendenciaService.cadastrar(new DadosCadastroListaPendencia(
				pedido.getId(),
				pedido.getDataPedido(),
				SituacaoListaPendenciaEnum.PENDENTE,
				listaPendenciaLivro,
				listaPendenciaKitLivro
				)
		);
	}

	public void atualizarInformacoes(Pedido pedido, DadosAtualizacaoPedido dados) {
		if (dados.dataPedido() != null) {
			pedido.setDataPedido(dados.dataPedido());
		}

		if (dados.dataEntrega() != null) {
			var dataPedido = dados.dataPedido() != null ? dados.dataPedido() : pedido.getDataPedido();
			if (dados.dataEntrega().isBefore(dataPedido)) {
				throw new ValidacaoException("A data de entrega não pode ser inferior a data do pedido.");
			}

			pedido.setDataEntrega(dados.dataEntrega());
		}

		if (dados.desconto() != null) {
			if (dados.desconto() < 0) {
				throw new ValidacaoException("Não é possível aplicar um desconto negativo ao pedido.");
			}

			pedido.setDesconto(dados.desconto());
		}

		if (dados.situacaoPedido() != null) {
			if (dados.situacaoPedido() == SituacaoPedidoEnum.FINALIZADO &&
					pedido.hasPendenciaAtivaBySituacao(SituacaoListaPendenciaEnum.PENDENTE)
			) {
				throw new ValidacaoException("Não é possível entregar o pedido pois o mesmo possui pendência ativa.");
			}

			pedido.setSituacaoPedido(dados.situacaoPedido());
		}

		if (dados.formaPagamento() != null) {
			pedido.setFormaPagamento(dados.formaPagamento());
		}

		pedido.setDataAtualizacao(LocalDateTime.now());
	}

	public void cancelar(Pedido pedido, DadosCancelamentoPedido dados) {
		if (pedido.hasPendenciaAtivaBySituacao(SituacaoListaPendenciaEnum.PENDENTE)) {
			var listaPendencia = pedido.getListaPendencia();
			listaPendencia.setSituacao(SituacaoListaPendenciaEnum.CANCELADA);
			listaPendencia.setAtivo(false);
		}

		if (dados.atualizarEstoque()) {
			for (PedidoLivro pedidoLivro : pedido.getPedidoLivro()) {
				var livro = pedidoLivro.getLivro();
				var quantidadeAtual = livro.getQuantidadeDisponivel() + pedidoLivro.getQuantidadeEntregue();
				livroService.atualizarQuantidade(livro.getId(),quantidadeAtual);
				livro.setDataAtualizacao(LocalDateTime.now());
			}

			for (PedidoKitLivro pedidoKitLivro : pedido.getPedidoKitLivro()) {
				var kitLivro = pedidoKitLivro.getKitLivro();
				var quantidadeAtual = kitLivro.getQuantidadeDisponivel() + pedidoKitLivro.getQuantidadeEntregue();
				kitLivroService.atualizarQuantidade(kitLivro.getId(),quantidadeAtual);
				kitLivro.setDataAtualizacao(LocalDateTime.now());
			}
		}

		pedido.setAtivo(false);
		pedido.setSituacaoPedido(SituacaoPedidoEnum.CANCELADO);
	}

	public Page<DadosListagemPedido> listarPedidosPorKitLivro(Long kitLivroId, Pageable pageable) {
		return pedidoRepository.findByAtivoTrueAndKitLivroId(kitLivroId, pageable)
				.map(DadosListagemPedido::new);
	}
}
