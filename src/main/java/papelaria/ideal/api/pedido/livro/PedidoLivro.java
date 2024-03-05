package papelaria.ideal.api.pedido.livro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import papelaria.ideal.api.livro.Livro;
import papelaria.ideal.api.pedido.Pedido;

import java.util.Objects;

@Data
@Entity(name = "pedido_livro")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class PedidoLivro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pedido_id")
	private Pedido pedido;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "livro_id")
	private Livro livro;

	private Long quantidade;
	private Long quantidadeEntregue;
	private Float valorUnitario;

	public Float getValorTotalPedido() {
		if (this.livro == null) {
			return 0.0F;
		}

		return this.quantidade * this.valorUnitario;
	}

	public Boolean todosItensEntregues() {
		return Objects.equals(this.quantidade, this.quantidadeEntregue);
	}
}
