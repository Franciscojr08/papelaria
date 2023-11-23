package papelaria.ideal.api.livro;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import papelaria.ideal.api.Serie.SerieRepository;
import papelaria.ideal.api.errors.ValidacaoException;
import papelaria.ideal.api.utils.LivroKitLivroServiceInterface;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class LivroService implements LivroKitLivroServiceInterface {

	@Autowired
	private LivroRepository livroRepository;
	@Autowired
	private SerieRepository serieRepository;

	@Transactional
	public void atualizarQuantidade(Long id, Long quantidade) {
		var livro = livroRepository.getReferenceById(id);
		livro.setQuantidadeDisponivel(quantidade);
	}

	public void cadastrar(DadosCadastroLivro dados) {
		validarIntegridade(dados);
		cadastrarLivro(dados);
	}

	private void validarIntegridade(DadosCadastroLivro dados) {
		if (livroRepository.existsByIdentificador(dados.identificador())) {
			throw new ValidacaoException(
					"Não foi possível cadastrar o livro. O identificador informado já está sendo utilizado em outro livro."
			);
		}

		if (dados.serieId() != null && !serieRepository.existsById(dados.serieId())) {
			throw new ValidacaoException(
					"Não foi possível cadastrar o livro. A serie informada é inválida ou não está cadastrada."
			);
		}
	}

	private void cadastrarLivro(DadosCadastroLivro dados) {
		var livro = new Livro();
		livro.setIdentificador(dados.identificador());
		livro.setNome(dados.nome());
		livro.setUsoInterno(dados.usoInterno());
		livro.setValor(dados.valor());
		livro.setQuantidadeDisponivel(dados.quantidadeDisponivel());
		livro.setDataCadastro(LocalDateTime.now());
		livro.setAtivo(true);

		if (dados.serieId() != null) {
			var serie = serieRepository.getReferenceById(dados.serieId());
			livro.setSerie(serie);
		}

		livroRepository.save(livro);
	}

	public void atualizarInformacoes(Livro livro, DadosAtualizacaoLivro dados) {
		if (dados.identificador() != null &&
				!Objects.equals(livro.getIdentificador(), dados.identificador()) &&
				livroRepository.existsByIdentificador(dados.identificador())
		) {
			throw new ValidacaoException(
					"Não foi possível atualizar o livro. O identificador informado já está sendo utilizado em outro livro."
			);
		}

		if (dados.serieId() != null &&
				!Objects.equals(livro.getSerie().getId(), dados.serieId()) &&
				!serieRepository.existsById(dados.serieId())
		) {
			throw new ValidacaoException(
					"Não foi possível atualizar o livro. A serie informada é inválida ou não está cadastrada."
			);
		}

		if (dados.identificador() != null) {
			livro.setIdentificador(dados.identificador());
		}

		if (dados.nome() != null) {
			livro.setNome(dados.nome());
		}

		if (dados.usoInterno() != null) {
			livro.setUsoInterno(dados.usoInterno());
		}

		if (dados.valor() != null) {
			if (dados.valor() < 0) {
				throw new ValidacaoException(
						"Não foi possível atualizar o livro. O valor tem que ser maior que zero."
				);
			}

			livro.setValor(dados.valor());
		}

		if (dados.quantidadeDisponivel() != null) {
			if (dados.quantidadeDisponivel() < 0) {
				throw new ValidacaoException(
						"Não foi possível atualizar o livro. A quantidade disponível tem que ser maior que zero."
				);
			}

			livro.setQuantidadeDisponivel(dados.quantidadeDisponivel());
		}

		if (dados.serieId() != null) {
			var serie = serieRepository.getReferenceById(dados.serieId());
			livro.setSerie(serie);
		}

		livro.setDataAtualizacao(LocalDateTime.now());
	}
}
