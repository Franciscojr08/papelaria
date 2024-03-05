package papelaria.ideal.api.Turma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import papelaria.ideal.api.Serie.SerieRepository;
import papelaria.ideal.api.Turma.records.DadosAtualizacaoTurma;
import papelaria.ideal.api.Turma.records.DadosCadastroTurma;
import papelaria.ideal.api.aluno.Aluno;
import papelaria.ideal.api.errors.ValidacaoException;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class TurmaService {

	@Autowired
	private TurmaRepository turmaRepository;
	@Autowired
	private SerieRepository serieRepository;

	public void cadastrar(DadosCadastroTurma dados) {
		validarIntegridade(dados.nome(), dados.serieId());
		cadastrarTurma(dados);
	}

	private void validarIntegridade(String nomeTurma, Long serieId) {
		if (!serieRepository.existsByIdAndAtivoTrue(serieId)) {
			throw new ValidacaoException(
					"Não foi possível cadastrar a turma. A série informada está inativa ou não esta cadastrada."
			);
		}

		if (turmaRepository.existsByNomeAndSerieIdAndAtivoTrue(nomeTurma,serieId)) {
			throw new ValidacaoException(
					"Não foi possível cadastrar a turma. Já existe uma turma para essa série com esse mesmo nome."
			);
		}
	}

	private void cadastrarTurma(DadosCadastroTurma dados) {
		var serie = serieRepository.getReferenceById(dados.serieId());
		var turma = new  Turma();
		turma.setNome(dados.nome());
		turma.setSerie(serie);
		turma.setDataCadastro(LocalDateTime.now());
		turma.setAtivo(true);

		turmaRepository.save(turma);
	}

	public void atualizarInformacoes(Turma turma, DadosAtualizacaoTurma dados) {
		if (!Objects.equals(turma.getNome(), dados.nome()) &&
				turmaRepository.existsByNomeAndAtivoTrue(dados.nome())
		) {
			throw new ValidacaoException(
					"Não foi possível atualizar a turma. Já existe uma turma com esse mesmo nome."
			);
		}

		if (!Objects.equals(turma.getSerie().getId(), dados.serieId()) &&
				!serieRepository.existsByIdAndAtivoTrue(dados.serieId())
		) {
			throw new ValidacaoException(
					"Não foi possível atualizar a turma. A série informada está inativa ou não esta cadastrada."
			);
		}

		if (!Objects.equals(turma.getSerie().getId(), dados.serieId())) {
			var serie = serieRepository.getReferenceById(dados.serieId());
			turma.setSerie(serie);
		}

		if (dados.nome() != null) {
			turma.setNome(dados.nome());
		}

		if (dados.nome() != null || !Objects.equals(turma.getSerie().getId(), dados.serieId())) {
			turma.setDataAtualizacao(LocalDateTime.now());
		}
	}

	public void deletar(Turma turma) {
		if (turma.getAlunos().stream().anyMatch(Aluno::getAtivo)) {
			throw new ValidacaoException(
					"Não foi possível deletar a turma, a mesma está sendo utilizada em um ou mais alunos ativos."
			);
		}

		turma.setAtivo(false);
		turma.setDataAtualizacao(LocalDateTime.now());
	}
}
