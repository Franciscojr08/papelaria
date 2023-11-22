package papelaria.ideal.api.pedido.kitLivro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import papelaria.ideal.api.kitLivro.KitLivro;
import papelaria.ideal.api.pedido.Pedido;

@Data
@Entity(name = "pedido_kit_livro")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class PedidoKitLivro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pedido_id")
	private Pedido pedido;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kit_livro_id")
	private KitLivro kitLivro;

	private Long quantidade;
	private Float valorUnitario;
}
