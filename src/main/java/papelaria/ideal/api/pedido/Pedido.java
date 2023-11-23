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
import papelaria.ideal.api.listaPendencia.ListaPendencia;
import papelaria.ideal.api.pedido.kitLivro.DadosPedidoKitLivro;
import papelaria.ideal.api.pedido.kitLivro.PedidoKitLivro;
import papelaria.ideal.api.pedido.livro.DadosPedidoLivro;
import papelaria.ideal.api.pedido.livro.PedidoLivro;
import papelaria.ideal.api.utils.FormaPagamentoEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
			Float valor,
			Float desconto,
			FormaPagamentoEnum formaPagamento,
			SituacaoPedidoEnum situacaoPedido,
			LocalDateTime dataEntrega,
			Cliente cliente,
			Boolean ativo
	) {
		this.dataPedido = dataPedido;
		this.valor = valor;
		this.desconto = desconto;
		this.formaPagamento = formaPagamento;
		this.situacaoPedido = situacaoPedido;
		this.dataEntrega = dataEntrega;
		this.cliente = cliente;
		this.ativo = ativo;
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
					pedidoKitLivro.getValorUnitario(),
					pedidoKitLivro.getQuantidade() * pedidoKitLivro.getValorUnitario()
			);

			dadosPedidoKitLivroList.add(dadosPedidoKitLivro);
		}

		return dadosPedidoKitLivroList;
	}

	public void atualizarInformacoes(DadosAtualizacaoPedido dados) {
		if (dados.dataPedido() != null) {
			this.dataPedido = dados.dataPedido();
		}

		if (dados.dataEntrega() != null) {
			if (dados.dataEntrega().isBefore(this.dataPedido)) {
				throw new ValidacaoException("A data de entrega não pode ser inferior a data do pedido.");
			}

			this.dataEntrega = dados.dataEntrega();
		}

		if (dados.desconto() != null) {
			this.desconto = dados.desconto();
		}

		if (dados.situacaoPedido() != null) {
			this.situacaoPedido = dados.situacaoPedido();
		}

		if (dados.formaPagamento() != null) {
			this.formaPagamento = dados.formaPagamento();
		}

		this.dataAtualizacao = LocalDateTime.now();
	}
}
