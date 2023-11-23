package papelaria.ideal.api.Serie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import papelaria.ideal.api.errors.ValidacaoException;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class SerieService {

	@Autowired
	private SerieRepository serieRepository;

	public void cadastrar(DadosCadastroSerie dados) {
		if (serieRepository.existsByNome(dados.nome())) {
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
		if (!serie.getNome().equals(dados.nome()) && serieRepository.existsByNome(String.valueOf(dados.nome()))) {
			throw new ValidacaoException("Já existe uma série cadastrada com esse nome.");
		}

		if (dados.nome() != null) {
			serie.setNome(dados.nome());
		}

		if (dados.ativo() != null) {
			serie.setAtivo(dados.ativo());
		}

		if (dados.nome() != null || dados.ativo() != null) {
			serie.setDataAtualizacao(LocalDateTime.now());
		}
	}
}
