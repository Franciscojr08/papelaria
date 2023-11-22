package aluno;

import turma.Turma;

public record DadosListagemAluno(Long id, String nome, String matricula, Turma turma) {
}
