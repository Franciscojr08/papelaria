package papelaria.ideal.api.Turma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import papelaria.ideal.api.Serie.SerieRepository;
import papelaria.ideal.api.errors.ValidacaoException;

import java.time.LocalDateTime;

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
		if (!serieRepository.existsById(serieId)) {
			throw new ValidacaoException(
					"Não foi possível cadastrar a turma. A série informada é inválida ou não esta cadastrada."
			);
		}

		if (turmaRepository.existsByNomeAndSerieId(nomeTurma,serieId)) {
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
		if (turma.getNome().equals(dados.nome()) && turmaRepository.existsByNome(dados.nome())) {
			throw new ValidacaoException(
					"Não foi possível atualizar a turma. Já existe uma turma com esse mesmo nome."
			);
		}

		if (dados.nome() != null) {
			turma.setNome(dados.nome());
		}

		if (dados.ativo() != null) {
			turma.setAtivo(dados.ativo());
		}

		if (dados.nome() != null || dados.ativo() != null) {
			turma.setDataAtualizacao(LocalDateTime.now());
		}
	}
}
