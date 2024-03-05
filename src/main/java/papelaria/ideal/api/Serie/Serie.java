package papelaria.ideal.api.Serie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import papelaria.ideal.api.Turma.Turma;
import papelaria.ideal.api.Turma.records.DadosListagemTurma;
import papelaria.ideal.api.livro.Livro;
import papelaria.ideal.api.livro.records.DadosListagemLivro;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

	public List<DadosListagemTurma> getDadosTurma() {
		if (this.turmas == null) {
			return new ArrayList<>();
		}

		List<DadosListagemTurma> dadosListagemTurmaList = new ArrayList<>();

		for (Turma turma : this.turmas) {
			if (!turma.getAtivo()) {
				continue;
			}

			dadosListagemTurmaList.add(new DadosListagemTurma(turma));
		}

		return dadosListagemTurmaList;
	}

	public Long getQuantidadeTurmasAtivas() {
		if (this.turmas == null) {
			return 0L;
		}

		return this.turmas.stream().filter(Turma::getAtivo).count();
	}

	public List<DadosListagemLivro> getDadosLivro() {
		if (this.livros == null) {
			return new ArrayList<>();
		}

		List<DadosListagemLivro> dadosListagemLivroList = new ArrayList<>();

		for (Livro livro : this.livros) {
			if (!livro.getAtivo()) {
				continue;
			}

			dadosListagemLivroList.add(new DadosListagemLivro(livro));
		}

		return dadosListagemLivroList;
	}

	public Long getQuantidadeLivrosAtivos() {
		if (this.livros == null) {
			return 0L;
		}

		return this.livros.stream().filter(Livro::getAtivo).count();
	}
}
