package papelaria.ideal.api.aluno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import papelaria.ideal.api.Turma.TurmaRepository;
import papelaria.ideal.api.aluno.records.DadosAtualizacaoAluno;
import papelaria.ideal.api.aluno.records.DadosCadastroAluno;
import papelaria.ideal.api.cliente.ClienteRepository;
import papelaria.ideal.api.errors.ValidacaoException;
import papelaria.ideal.api.utils.Functions;

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

		if (Functions.isNotBlank(dados.matricula())) {
			aluno.setMatricula(dados.matricula().replaceAll("[.-]", ""));
		}

		if (Functions.isNotBlank(dados.rg())) {
			aluno.setRg(dados.rg().replaceAll("[.-]", ""));
		}

		if (Functions.isNotBlank(dados.cpf())) {
			aluno.setCpf(dados.cpf().replaceAll("[.-]", ""));
		}

		aluno.setCliente(cliente);
		aluno.setTurma(turma);
		aluno.setAtivo(true);
		aluno.setDataCadastro(LocalDateTime.now());

		alunoRepository.save(aluno);
	}

    private void validarIntegridade(DadosCadastroAluno dados) {
        if (!clienteRepository.existsByIdAndAtivoTrue(dados.clienteResponsavelId())) {
            throw new ValidacaoException(
                    "Não foi possível cadastrar o aluno. O cliente informado é inválido ou não está cadastrado."
            );
        }

		if (!turmaRepository.existsByIdAndAtivoTrue(dados.turmaId())) {
			throw new ValidacaoException(
					"Não foi possível cadastrar o aluno. A turma informada é inválida ou não está cadastrada."
			);
		}

        if (Functions.isBlank(dados.cpf()) && Functions.isBlank(dados.rg()) && Functions.isBlank(dados.matricula())) {
            throw new ValidacaoException(
                    "Não foi possível cadastrar o aluno. É necessário informar algum campo de identificação: " +
                            "RG, Matrícula ou CPF."
            );
        }

        if (Functions.isNotBlank(dados.matricula()) &&
		        alunoRepository.existsByMatriculaAndAtivoTrue(dados.matricula().replaceAll("[.-]", ""))
        ) {
            throw new ValidacaoException(
                    "Não foi possível cadastrar o aluno. A matricula informada já está sendo utilizada por outro aluno."
            );
        }

        if (Functions.isNotBlank(dados.rg()) &&
		        alunoRepository.existsByRgAndAtivoTrue(dados.rg().replaceAll("[.-]", ""))
        ) {
            throw new ValidacaoException(
                    "Não foi possível cadastrar o aluno. o RG informado já está sendo utilizado por outro aluno."
            );
        }

        if (Functions.isNotBlank(dados.cpf()) &&
		        alunoRepository.existsByCpfAndAtivoTrue(dados.cpf().replaceAll("[.-]", ""))
        ) {
            throw new ValidacaoException(
                    "Não foi possível cadastrar o aluno. O CPF informado já está sendo utilizado por outro aluno."
            );
        }
    }

	private void validarIntegridadeAtualizacao(Aluno aluno, DadosAtualizacaoAluno dados) {
		if (!clienteRepository.existsByIdAndAtivoTrue(dados.clienteResponsavelId())) {
			throw new ValidacaoException(
					"Não foi possível atualizar o aluno. O cliente informado é inválido ou não está cadastrado."
			);
		}

		if (!turmaRepository.existsByIdAndAtivoTrue(dados.turmaId())) {
			throw new ValidacaoException(
					"Não foi possível atualizar o aluno. A turma informada é inválida ou não está cadastrada."
			);
		}

		if (Functions.isBlank(dados.cpf()) && Functions.isBlank(dados.rg()) && Functions.isBlank(dados.matricula())) {
			throw new ValidacaoException(
					"Não foi possível atualizar o aluno. É necessário informar algum campo de identificação: " +
							"RG, Matrícula ou CPF."
			);
		}

		if (Functions.isNotBlank(dados.matricula()) &&
				!Objects.equals(aluno.getMatricula(), dados.matricula().replaceAll("[.-]", "")) &&
				alunoRepository.existsByMatriculaAndAtivoTrue(dados.matricula().replaceAll("[.-]", ""))
		) {
			throw new ValidacaoException(
					"Não foi possível atualizar o aluno. A matricula informada já está sendo utilizada por outro aluno."
			);
		}

		if (Functions.isNotBlank(dados.rg()) &&
				!Objects.equals(aluno.getRg(), dados.rg().replaceAll("[.-]", "")) &&
				alunoRepository.existsByRgAndAtivoTrue(dados.rg().replaceAll("[.-]", ""))
		) {
			throw new ValidacaoException(
					"Não foi possível atualizar o aluno. o RG informado já está sendo utilizado por outro aluno."
			);
		}

		if (Functions.isNotBlank(dados.cpf()) &&
				!Objects.equals(aluno.getCpf(), dados.cpf().replaceAll("[.-]", "")) &&
				alunoRepository.existsByCpfAndAtivoTrue(dados.cpf().replaceAll("[.-]", ""))
		) {
			throw new ValidacaoException(
					"Não foi possível atualizar o aluno. O CPF informado já está sendo utilizado por outro aluno."
			);
		}
	}

    public void atualizarInformacoes(Aluno aluno, DadosAtualizacaoAluno dados) {
        validarIntegridadeAtualizacao(aluno,dados);

	    if (Functions.isNotBlank(dados.nome())) {
		    aluno.setNome(dados.nome());
	    }

	    if (Functions.isNotBlank(dados.matricula())) {
		    aluno.setMatricula(dados.matricula().replaceAll("[.-]", ""));
	    }

	    if (Functions.isNotBlank(dados.rg())) {
		    aluno.setRg(dados.rg().replaceAll("[.-]", ""));
	    }

	    if (Functions.isNotBlank(dados.cpf())) {
		    aluno.setCpf(dados.cpf().replaceAll("[.-]", ""));
	    }

	    if (Functions.isNotBlankLong(dados.clienteResponsavelId())) {
		    var cliente = clienteRepository.getReferenceById(dados.clienteResponsavelId());
		    aluno.setCliente(cliente);
	    }

		if (Functions.isNotBlankLong(dados.turmaId())) {
			var turma = turmaRepository.getReferenceById(dados.turmaId());
			aluno.setTurma(turma);
		}

		aluno.setDataAtualizacao(LocalDateTime.now());
    }
}
