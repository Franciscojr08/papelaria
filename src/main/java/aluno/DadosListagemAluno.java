package aluno;

import cliente.Cliente;
import turma.Turma;

public record DadosListagemAluno(Long id, String nome, String matricula, Cliente cliente, Turma turma) {
    public DadosListagemAluno(Aluno aluno) {
        this(aluno.getId(), aluno.getNome(), aluno.getMatricula(), aluno.getCliente(), aluno.getTurma());
    }
}