package papelaria.ideal.api.Serie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import papelaria.ideal.api.Turma.Turma;
import papelaria.ideal.api.livro.Livro;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity(name = "serie")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Serie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "serie", fetch = FetchType.LAZY)
	private List<Turma> turmas;

	@OneToMany(mappedBy = "serie", fetch = FetchType.LAZY)
	private List<Livro> livros;

	private String nome;
	private LocalDateTime dataCadastro;
	private LocalDateTime dataAtualizacao;
	private Boolean ativo;
}
