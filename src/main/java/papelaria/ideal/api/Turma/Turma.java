package papelaria.ideal.api.Turma;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import papelaria.ideal.api.Serie.Serie;
import papelaria.ideal.api.aluno.Aluno;
import papelaria.ideal.api.aluno.records.DadosListagemAluno;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "turma")
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

	@OneToMany(mappedBy = "turma", fetch = FetchType.LAZY)
	private List<Aluno> alunos;

	private String nome;
	private LocalDateTime dataCadastro;
	private LocalDateTime dataAtualizacao;
	private Boolean ativo;

	public Long getQuantidadeAlunosAtivos() {
		if (this.getAlunos() == null) {
			return 0L;
		}

		return this.getAlunos().stream().filter(Aluno::getAtivo).count();
	}

	public List<DadosListagemAluno> getDadosAlunos() {
		if (this.getAlunos() == null) {
			return new ArrayList<>();
		}

		List<DadosListagemAluno> dadosListagemAlunoList = new ArrayList<>();

		for (Aluno aluno : this.getAlunos()) {
			if (!aluno.getAtivo()) {
				continue;
			}

			dadosListagemAlunoList.add(new DadosListagemAluno(aluno));
		}

		return dadosListagemAlunoList;
	}
}
