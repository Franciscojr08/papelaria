package papelaria.ideal.api.pedido;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import papelaria.ideal.api.cliente.Cliente;
import papelaria.ideal.api.utils.FormaPagamentoEnum;

import java.time.LocalDateTime;

@Data
@Entity(name = "pedido")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime dataPedido;
	private float valor;
	private float desconto;

	@Enumerated(EnumType.STRING)
	private FormaPagamentoEnum formaPagamento;

	@Enumerated(EnumType.STRING)
	private SituacaoPedidoEnum situacaoPedido;

	private LocalDateTime dataEntrega;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clienteId")
	private Cliente cliente;

	private boolean ativo;
}
