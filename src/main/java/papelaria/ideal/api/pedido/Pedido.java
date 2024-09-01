package papelaria.ideal.api.pedido;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import papelaria.ideal.api.cliente.Cliente;
import papelaria.ideal.api.errors.ValidacaoException;
import papelaria.ideal.api.kitLivro.KitLivro;
import papelaria.ideal.api.listaPendencia.ListaPendencia;
import papelaria.ideal.api.listaPendencia.SituacaoListaPendenciaEnum;
import papelaria.ideal.api.listaPendencia.listaPendenciaKitLivro.ListaPendenciaKitLivro;
import papelaria.ideal.api.listaPendencia.listaPendenciaLivro.ListaPendenciaLivro;
import papelaria.ideal.api.livro.Livro;
import papelaria.ideal.api.pedido.kitLivro.DadosPedidoKitLivro;
import papelaria.ideal.api.pedido.kitLivro.PedidoKitLivro;
import papelaria.ideal.api.pedido.livro.DadosPedidoLivro;
import papelaria.ideal.api.pedido.livro.PedidoLivro;
import papelaria.ideal.api.pedido.records.DadosAtualizacaoPedido;
import papelaria.ideal.api.pedido.records.DadosPedidoLivroKitLivro;
import papelaria.ideal.api.utils.FormaPagamentoEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Entity(name = "pedido")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Float valor;
	private Float desconto;

	@Enumerated(EnumType.STRING)
	private SituacaoPedidoEnum situacaoPedido;

	@Enumerated(EnumType.STRING)
	private FormaPagamentoEnum formaPagamento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	private LocalDateTime dataPedido;
	private LocalDateTime dataEntrega;
	private LocalDateTime dataAtualizacao;
	private Boolean ativo;

	@Cascade({CascadeType.ALL})
	@OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
	private List<PedidoLivro> pedidoLivro = new ArrayList<>();

	@Cascade({CascadeType.ALL})
	@OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
	private List<PedidoKitLivro> pedidoKitLivro = new ArrayList<>();

	@OneToOne(mappedBy = "pedido", fetch = FetchType.LAZY)
	private ListaPendencia listaPendencia;

	public Pedido(
			LocalDateTime dataPedido,
			Float desconto,
			FormaPagamentoEnum formaPagamento,
			SituacaoPedidoEnum situacaoPedido,
			Cliente cliente,
			Boolean ativo
	) {
		this.dataPedido = dataPedido;
		this.desconto = desconto;
		this.formaPagamento = formaPagamento;
		this.situacaoPedido = situacaoPedido;
		this.cliente = cliente;
		this.ativo = ativo;
	}

	public Boolean hasPendenciaAtivaBySituacao(SituacaoListaPendenciaEnum situacao) {
		return this.listaPendencia != null &&
				this.listaPendencia.getAtivo() &&
				this.listaPendencia.getSituacao() == situacao;
	}

	public Float getValorTotal() {
		if (desconto.isNaN()) {
			return valor;
		}

		var desconto = valor * (this.desconto / 100);
		return valor - desconto;
	}

	public List<DadosPedidoLivro> getDadosPedidoLivro() {
		if (this.pedidoLivro == null) {
			return new ArrayList<>();
		}

		List<DadosPedidoLivro> dadosPedidoLivroList = new ArrayList<>();

		for (PedidoLivro pedidoLivro : this.pedidoLivro) {
			var dadosPedidoLivro = new DadosPedidoLivro(
					pedidoLivro.getId(),
					pedidoLivro.getLivro().getIdentificador(),
					pedidoLivro.getLivro().getNome(),
					pedidoLivro.getQuantidade(),
					pedidoLivro.getQuantidadeEntregue(),
					pedidoLivro.getValorUnitario(),
					pedidoLivro.getQuantidade() * pedidoLivro.getValorUnitario()
			);

			dadosPedidoLivroList.add(dadosPedidoLivro);
		}

		return dadosPedidoLivroList;
	}

	public List<DadosPedidoKitLivro> getDadosPedidoKitLivro() {
		if (this.pedidoKitLivro == null) {
			return new ArrayList<>();
		}

		List<DadosPedidoKitLivro> dadosPedidoKitLivroList = new ArrayList<>();

		for (PedidoKitLivro pedidoKitLivro : this.pedidoKitLivro) {
			var dadosPedidoKitLivro = new DadosPedidoKitLivro(
					pedidoKitLivro.getKitLivro().getId(),
					pedidoKitLivro.getKitLivro().getNome(),
					pedidoKitLivro.getQuantidade(),
					pedidoKitLivro.getQuantidadeEntregue(),
					pedidoKitLivro.getValorUnitario(),
					pedidoKitLivro.getQuantidade() * pedidoKitLivro.getValorUnitario()
			);

			dadosPedidoKitLivroList.add(dadosPedidoKitLivro);
		}

		return dadosPedidoKitLivroList;
	}

	public Boolean todosItensEntregues() {
		var todosLivrosEntregues = this.pedidoLivro.stream().allMatch(PedidoLivro::todosItensEntregues);
		var todosKitLivrosEntregues = this.pedidoKitLivro.stream().allMatch(PedidoKitLivro::todosItensEntregues);

		return todosLivrosEntregues && todosKitLivrosEntregues;
	}

	public Boolean algumItemEntregue() {
		var livrosEntregues = this.pedidoLivro.stream().mapToLong(PedidoLivro::getQuantidadeEntregue).sum();
		var kitLivrosEntregues = this.pedidoKitLivro.stream().mapToLong(PedidoKitLivro::getQuantidadeEntregue).sum();

		return (livrosEntregues > 0) || (kitLivrosEntregues > 0);
	}

	public Boolean todosItensEstaoNaListaPendencia() {
		var listaPendenciaLivro = this.listaPendencia.getListaPendenciaLivro();
		var totalLivrosPendencia = listaPendenciaLivro.stream().mapToLong(ListaPendenciaLivro::getQuantidade).sum();
		var totalLivrosPedido = this.pedidoLivro.stream().mapToLong(PedidoLivro::getQuantidade).sum();
		var todosLivrosPendentes = (totalLivrosPendencia == totalLivrosPedido);

		var listaPendenciaKitLivro = this.listaPendencia.getListaPendenciaKitLivro();
		var totalKitLivroPendencia = listaPendenciaKitLivro.stream().mapToLong(ListaPendenciaKitLivro::getQuantidade).sum();
		var totalKitLivroPedido = this.pedidoKitLivro.stream().mapToLong(PedidoKitLivro::getQuantidade).sum();
		var todosKitLivrosPendentes = (totalKitLivroPendencia == totalKitLivroPedido);

		return todosLivrosPendentes && todosKitLivrosPendentes;
	}

	public void atualizarPedidoLivro(DadosPedidoLivroKitLivro pedidoLivroKitLivro, PedidoLivro pedidoLivro) {
		var quantidadeEntregue = pedidoLivro.getQuantidadeEntregue();
		var quantidadePendente = pedidoLivro.getQuantidade() - quantidadeEntregue;

		if (pedidoLivroKitLivro.quantidade() > quantidadePendente) {
			throw new ValidacaoException(
					"Não foi possível atualizar a lista de pendência. A quantidade de livros" +
							" informada nesta atualização é maior do que a quantidade pendente."
			);
		}

		pedidoLivro.setQuantidadeEntregue(quantidadeEntregue + pedidoLivroKitLivro.quantidade());
	}

	public void atualizarPedidoKitLivro(DadosPedidoLivroKitLivro pedidoLivroKitLivro, PedidoKitLivro pedidoKitLivro) {
		var quantidadeEntregue = pedidoKitLivro.getQuantidadeEntregue();
		var quantidadePendente = pedidoKitLivro.getQuantidade() - quantidadeEntregue;

		if (pedidoLivroKitLivro.quantidade() > quantidadePendente) {
			throw new ValidacaoException(
					"Não foi possível atualizar a lista de pendência. A quantidade de kit de livros" +
							" informada nesta atualização é maior do que a quantidade pendente."
			);
		}

		pedidoKitLivro.setQuantidadeEntregue(quantidadeEntregue + pedidoLivroKitLivro.quantidade());
	}

	public Boolean isEntregue() {
		return this.situacaoPedido == SituacaoPedidoEnum.FINALIZADO;
	}

	public void removerQuantidadeDeLivroEntregue(Livro livro, Long quantidade) {
		if (quantidade < 0) {
			return;
		}

		Optional<PedidoLivro> pedidoLivroOptional = this.pedidoLivro.stream()
				.filter(pedidoLivro -> pedidoLivro.getLivro().getId() == livro.getId())
				.findFirst();

		if (pedidoLivroOptional.isPresent()) {
			var pedidoLivro = pedidoLivroOptional.get();
			var quantidadeEntregue = pedidoLivro.getQuantidadeEntregue();
			var quantidadeEntregueAtual = quantidadeEntregue - quantidade;
			pedidoLivro.setQuantidadeEntregue(quantidadeEntregueAtual);
		} else {
			throw new ValidacaoException("O livro informado na devolução não foi encontrado.");
		}
	}

	public void removerQuantidadeDeKitLivroEntregue(KitLivro kitLivro, Long quantidade) {
		if (quantidade < 0) {
			return;
		}

		Optional<PedidoKitLivro> pedidoKitLivroOptional = this.pedidoKitLivro.stream()
				.filter(pedidoKitlivro -> pedidoKitlivro.getKitLivro().getId() == kitLivro.getId())
				.findFirst();

		if (pedidoKitLivroOptional.isPresent()) {
			var pedidoKitlivro = pedidoKitLivroOptional.get();
			var quantidadeEntregue = pedidoKitlivro.getQuantidadeEntregue();
			var quantidadeEntregueAtual = quantidadeEntregue - quantidade;
			pedidoKitlivro.setQuantidadeEntregue(quantidadeEntregueAtual);
		} else {
			throw new ValidacaoException("O kit de livro informado na devolução não foi encontrado.");
		}
	}

	public Long getTotalKitsPendentes() {
		var quantidade =  this.getListaPendencia().getQuantidadeKitLivrosByEntregueTrueOrFalse(false);
		var quantidadeEntregue =  this.getListaPendencia().getQuantidadeKitLivrosByEntregueTrueOrFalse(true);

		return quantidade - quantidadeEntregue;
	}

	public Long getTotalLivrosPendentes() {
		var quantidade =  this.getListaPendencia().getQuantidadeLivrosByEntregueTrueOrFalse(false);
		var quantidadeEntregue =  this.getListaPendencia().getQuantidadeLivrosByEntregueTrueOrFalse(true);

		return quantidade - quantidadeEntregue;
	}

	public Long getTotalPendencias() {
		return this.getTotalKitsPendentes() + this.getTotalLivrosPendentes();
	}
}
