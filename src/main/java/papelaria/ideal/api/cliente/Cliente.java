package papelaria.ideal.api.cliente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "cliente")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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
	private Boolean ativo;
}
