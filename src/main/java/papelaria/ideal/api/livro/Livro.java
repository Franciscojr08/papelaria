package papelaria.ideal.api.livro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import papelaria.ideal.api.pedido.Pedido;
import papelaria.ideal.api.pedido.livro.PedidoLivro;

import java.util.List;

@Data
@Entity(name = "livro")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Livro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "livro", fetch = FetchType.LAZY)
	private List<PedidoLivro> pedidoLivro;

	private String nome;
	private Boolean ativo;
	private String identificador;
	private Long quantidadeDisponivel;
}
