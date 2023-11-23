package papelaria.ideal.api.aluno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import papelaria.ideal.api.cliente.ClienteRepository;

import java.time.LocalDateTime;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    public void cadastrar(DadosCadastroAluno dados) {
        validarIntegridade(dados);
    }

	private void cadastrarAluno(DadosCadastroAluno dados) {
		var cliente = clienteRepository.getReferenceById(dados.clienteResponsavelId());
		var aluno = new Aluno();
		aluno.setNome(dados.nome());
		aluno.setMatricula(dados.matricula());
		aluno.setRg(dados.rg());
		aluno.setCpf(dados.cpf());
		aluno.setCliente(cliente);
		aluno.setAtivo(true);
		aluno.setDataCadastro(LocalDateTime.now());

		alunoRepository.save(aluno);
	}

    private void validarIntegridade(DadosCadastroAluno dados) {
        if (!clienteRepository.existsById(dados.clienteResponsavelId())) {
            throw new RuntimeException(
                    "Não foi possível cadastrar o aluno. O cliente informado é inválido ou não está cadastrado."
            );
        }

        if (dados.cpf() == null && dados.rg() == null && dados.matricula() == null) {
            throw new RuntimeException(
                    "Não foi possível cadastrar o aluno. É necessário informar algum campo de identificação: " +
                            "RG, Matrícula ou CPF."
            );
        }

        if (dados.matricula() != null && alunoRepository.existsByMatricula(dados.matricula())) {
            throw new RuntimeException(
                    "Não foi possível cadastrar o aluno. A matricula informada já está sendo utilizada por outro aluno."
            );
        }

        if (dados.rg() != null && alunoRepository.existsByRg(dados.rg())) {
            throw new RuntimeException(
                    "Não foi possível cadastrar o aluno. o RG informado já está sendo utilizado por outro aluno."
            );
        }

        if (dados.cpf() != null && alunoRepository.existsByCpf(dados.cpf())) {
            throw new RuntimeException(
                    "Não foi possível cadastrar o aluno. O CPF informado já está sendo utilizado por outro aluno."
            );
        }
    }

	private void validarIntegridadeAtualizacao(Aluno aluno, DadosAtualizacaoAluno dados) {
		if (!clienteRepository.existsById(dados.clienteResponsavelId())) {
			throw new RuntimeException(
					"Não foi possível atualizar o aluno. O cliente informado é inválido ou não está cadastrado."
			);
		}

		if (dados.cpf() == null && dados.rg() == null && dados.matricula() == null) {
			throw new RuntimeException(
					"Não foi possível atualizar o aluno. É necessário informar algum campo de identificação: " +
							"RG, Matrícula ou CPF."
			);
		}

		if (dados.matricula() != null &&
				!aluno.getMatricula().equals(dados.matricula()) &&
				alunoRepository.existsByMatricula(dados.matricula())
		) {
			throw new RuntimeException(
					"Não foi possível atualizar o aluno. A matricula informada já está sendo utilizada por outro aluno."
			);
		}

		if (dados.rg() != null &&
				!aluno.getRg().equals(dados.rg()) &&
				alunoRepository.existsByRg(dados.rg())
		) {
			throw new RuntimeException(
					"Não foi possível atualizar o aluno. o RG informado já está sendo utilizado por outro aluno."
			);
		}

		if (dados.cpf() != null &&
				!aluno.getCpf().equals(dados.cpf())
				&& alunoRepository.existsByCpf(dados.cpf())
		) {
			throw new RuntimeException(
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

		aluno.setDataAtualizacao(LocalDateTime.now());
    }
}
