package papelaria.ideal.api.pedido;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import papelaria.ideal.api.cliente.Cliente;
import papelaria.ideal.api.pedido.kitLivro.PedidoKitLivro;
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

	@Enumerated(EnumType.STRING)
	private FormaPagamentoEnum formaPagamento;

	@Enumerated(EnumType.STRING)
	private SituacaoPedidoEnum situacaoPedido;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@Cascade({CascadeType.ALL})
	@OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
	private List<PedidoLivro> pedidoLivro = new ArrayList<>();

	@Cascade({CascadeType.ALL})
	@OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
	private List<PedidoKitLivro> pedidoKitLivro = new ArrayList<>();

	private LocalDateTime dataPedido;
	private Float valor;
	private Float desconto;
	private LocalDateTime dataEntrega;
	private Boolean ativo;

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
}
