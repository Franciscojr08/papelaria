package papelaria.ideal.api.aluno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import papelaria.ideal.api.Turma.TurmaRepository;
import papelaria.ideal.api.cliente.ClienteRepository;
import papelaria.ideal.api.errors.ValidacaoException;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
	@Autowired
	private TurmaRepository turmaRepository;

    public void cadastrar(DadosCadastroAluno dados) {
        validarIntegridade(dados);
		cadastrarAluno(dados);
    }

	private void cadastrarAluno(DadosCadastroAluno dados) {
		var turma = turmaRepository.getReferenceById(dados.turmaId());
		var cliente = clienteRepository.getReferenceById(dados.clienteResponsavelId());
		var aluno = new Aluno();
		aluno.setNome(dados.nome());
		aluno.setMatricula(dados.matricula());
		aluno.setRg(dados.rg());
		aluno.setCpf(dados.cpf());
		aluno.setCliente(cliente);
		aluno.setTurma(turma);
		aluno.setAtivo(true);
		aluno.setDataCadastro(LocalDateTime.now());

		alunoRepository.save(aluno);
	}

    private void validarIntegridade(DadosCadastroAluno dados) {
        if (!clienteRepository.existsById(dados.clienteResponsavelId())) {
            throw new ValidacaoException(
                    "Não foi possível cadastrar o aluno. O cliente informado é inválido ou não está cadastrado."
            );
        }

		if (!turmaRepository.existsById(dados.turmaId())) {
			throw new ValidacaoException(
					"Não foi possível cadastrar o aluno. A turma informada é inválida ou não está cadastrada."
			);
		}

        if (dados.cpf() == null && dados.rg() == null && dados.matricula() == null) {
            throw new ValidacaoException(
                    "Não foi possível cadastrar o aluno. É necessário informar algum campo de identificação: " +
                            "RG, Matrícula ou CPF."
            );
        }

        if (dados.matricula() != null && alunoRepository.existsByMatricula(dados.matricula())) {
            throw new ValidacaoException(
                    "Não foi possível cadastrar o aluno. A matricula informada já está sendo utilizada por outro aluno."
            );
        }

        if (dados.rg() != null && alunoRepository.existsByRg(dados.rg())) {
            throw new ValidacaoException(
                    "Não foi possível cadastrar o aluno. o RG informado já está sendo utilizado por outro aluno."
            );
        }

        if (dados.cpf() != null && alunoRepository.existsByCpf(dados.cpf())) {
            throw new ValidacaoException(
                    "Não foi possível cadastrar o aluno. O CPF informado já está sendo utilizado por outro aluno."
            );
        }
    }

	private void validarIntegridadeAtualizacao(Aluno aluno, DadosAtualizacaoAluno dados) {
		if (!clienteRepository.existsById(dados.clienteResponsavelId())) {
			throw new ValidacaoException(
					"Não foi possível atualizar o aluno. O cliente informado é inválido ou não está cadastrado."
			);
		}

		if (!turmaRepository.existsById(dados.turmaId())) {
			throw new ValidacaoException(
					"Não foi possível atualizar o aluno. A turma informada é inválida ou não está cadastrada."
			);
		}

		if (dados.cpf() == null && dados.rg() == null && dados.matricula() == null) {
			throw new ValidacaoException(
					"Não foi possível atualizar o aluno. É necessário informar algum campo de identificação: " +
							"RG, Matrícula ou CPF."
			);
		}

		if (dados.matricula() != null &&
				!Objects.equals(aluno.getMatricula(), dados.matricula()) &&
				alunoRepository.existsByMatricula(dados.matricula())
		) {
			throw new ValidacaoException(
					"Não foi possível atualizar o aluno. A matricula informada já está sendo utilizada por outro aluno."
			);
		}

		if (dados.rg() != null &&
				!Objects.equals(aluno.getRg(), dados.rg()) &&
				alunoRepository.existsByRg(dados.rg())
		) {
			throw new ValidacaoException(
					"Não foi possível atualizar o aluno. o RG informado já está sendo utilizado por outro aluno."
			);
		}

		if (dados.cpf() != null &&
				!Objects.equals(aluno.getCpf(), dados.cpf()) &&
				alunoRepository.existsByCpf(dados.cpf())
		) {
			throw new ValidacaoException(
					"Não foi possível atualizar o aluno. O CPF informado já está sendo utilizado por outro aluno."
			);
		}
	}

    public void atualizarInformacoes(Aluno aluno, DadosAtualizacaoAluno dados) {
        validarIntegridadeAtualizacao(aluno,dados);

	    if (dados.nome() != null) {
		    aluno.setNome(dados.nome());
	    }

	    if (dados.matricula() != null) {
		    aluno.setMatricula(dados.matricula());
	    }

	    if (dados.rg() != null) {
		    aluno.setRg(dados.rg());
	    }

	    if (dados.cpf() != null) {
		    aluno.setCpf(dados.cpf());
	    }

	    if (dados.clienteResponsavelId() != null) {
		    var cliente = clienteRepository.getReferenceById(dados.clienteResponsavelId());
		    aluno.setCliente(cliente);
	    }

		if (dados.turmaId() != null) {
			var turma = turmaRepository.getReferenceById(dados.turmaId());
			aluno.setTurma(turma);
		}

		aluno.setDataAtualizacao(LocalDateTime.now());
    }
}
