package papelaria.ideal.api.cliente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import papelaria.ideal.api.pedido.Pedido;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity(name = "cliente")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
	private List<Pedido> pedidos;

	private String nome;
	private String cpf;
	private String telefone;
	private String email;
	private String cep;
	private String logradouro;
	private String bairro;
	private String cidade;
	private String estado;
	private Boolean responsavelAluno;
	private LocalDateTime dataCadastro;
	private LocalDateTime dataAtualizacao;
	private Boolean ativo;
}
