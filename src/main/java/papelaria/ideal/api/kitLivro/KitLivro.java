package papelaria.ideal.api.kitLivro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import papelaria.ideal.api.pedido.kitLivro.PedidoKitLivro;

import java.util.List;

@Data
@Entity(name = "kit_livro")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class KitLivro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "kitLivro", fetch = FetchType.LAZY)
	private List<PedidoKitLivro> pedidoKitLivro;

	private String nome;
	private Boolean ativo;
	private Long quantidadeDisponivel;
}
