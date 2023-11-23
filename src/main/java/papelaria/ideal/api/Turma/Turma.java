package papelaria.ideal.api.Turma;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import papelaria.ideal.api.Serie.Serie;

import java.time.LocalDateTime;

@Data
@Entity(name = "serie")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Turma {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "serie_id")
	private Serie serie;

	private String nome;
	private LocalDateTime dataCadastro;
	private LocalDateTime dataAtualizacao;
	private Boolean ativo;
}
