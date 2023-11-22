package papelaria.ideal.api.livro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import papelaria.ideal.api.listaPendencia.listaPendenciaLivro.ListaPendenciaLivro;
import papelaria.ideal.api.pedido.livro.PedidoLivro;

import java.time.LocalDateTime;
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

	@OneToMany(mappedBy = "livro", fetch = FetchType.LAZY)
	private List<ListaPendenciaLivro> listaPendenciaLivro;

	private String identificador;
	private String nome;
	private Boolean usoInterno;
	private Float valor;
	private Long quantidadeDisponivel;
	private LocalDateTime dataCadastro;
	private LocalDateTime dataAtualizacao;
	private Boolean ativo;
}
