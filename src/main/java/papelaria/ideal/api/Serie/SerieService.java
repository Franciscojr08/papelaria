package papelaria.ideal.api.Serie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import papelaria.ideal.api.Serie.records.DadosAtualizacaoSerie;
import papelaria.ideal.api.Serie.records.DadosCadastroSerie;
import papelaria.ideal.api.Serie.records.DadosComboSerie;
import papelaria.ideal.api.Turma.Turma;
import papelaria.ideal.api.errors.ValidacaoException;
import papelaria.ideal.api.livro.Livro;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class SerieService {

	@Autowired
	private SerieRepository serieRepository;

	public void cadastrar(DadosCadastroSerie dados) {
		if (serieRepository.existsByNomeAndAtivoTrue(dados.nome())) {
			throw new ValidacaoException("Já existe uma série cadastrada com esse nome.");
		}

		cadastrarSerie(dados);
	}

	private void cadastrarSerie(DadosCadastroSerie dados) {
		var serie = new Serie();
		serie.setNome(String.valueOf(dados.nome()));
		serie.setAtivo(true);
		serie.setDataCadastro(LocalDateTime.now());

		serieRepository.save(serie);
	}

	public void atualizarInformacoes(Serie serie, DadosAtualizacaoSerie dados) {
		if (!Objects.equals(serie.getNome(), dados.nome()) &&
				serieRepository.existsByNomeAndAtivoTrue(String.valueOf(dados.nome()))
		) {
			throw new ValidacaoException("Já existe uma série cadastrada com esse nome.");
		}

		if (dados.nome() != null) {
			serie.setNome(dados.nome());
			serie.setDataAtualizacao(LocalDateTime.now());
		}
	}

	public void deletar(Serie serie) {
		if (serie.getTurmas().stream().anyMatch(Turma::getAtivo)) {
			throw new ValidacaoException(
					"Não foi possível deletar a série, a mesma está sendo utilizada em uma ou mais turmas ativas."
			);
		}

		if (serie.getLivros().stream().anyMatch(Livro::getAtivo)) {
			throw new ValidacaoException(
					"Não foi possível deletar a série, a mesma está sendo utilizada em um ou mais livros ativos."
			);
		}

		serie.setAtivo(false);
		serie.setDataAtualizacao(LocalDateTime.now());
	}

	public List<DadosComboSerie> montarCombo() {
		var series = serieRepository.findAllByAtivoTrue();
		return series.stream().map(DadosComboSerie::new).toList();
	}
}
