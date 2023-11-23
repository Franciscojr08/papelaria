package papelaria.ideal.api.kitLivro;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import papelaria.ideal.api.errors.ValidacaoException;
import papelaria.ideal.api.listaPendencia.ListaPendencia;
import papelaria.ideal.api.listaPendencia.SituacaoListaPendenciaEnum;
import papelaria.ideal.api.livro.DadosAtualizacaoLivro;
import papelaria.ideal.api.utils.LivroKitLivroServiceInterface;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class KitLivroService implements LivroKitLivroServiceInterface {

	@Autowired
	private KitLivroRepository kitLivroRepository;

	@Transactional
	public void atualizarQuantidade(Long id, Long quantidade) {
		var kitLivro = kitLivroRepository.getReferenceById(id);
		kitLivro.setQuantidadeDisponivel(quantidade);
	}

	public void cadastrar(DadosCadastroKitLivro dados) {
		if (kitLivroRepository.existsByNome(dados.nome())) {
			throw new ValidacaoException(
					"Não foi possível cadastrar o kit livro. O nome informado já está cadastrado"
			);
		}

		cadastrarKitLivro(dados);
	}

	private void cadastrarKitLivro(DadosCadastroKitLivro dados) {
		var kitLivro = new KitLivro();
		kitLivro.setNome(dados.nome());
		kitLivro.setDescricao(dados.descricao());
		kitLivro.setValor(dados.valor());
		kitLivro.setQuantidadeDisponivel(dados.quantidadeDisponivel());
		kitLivro.setDataCadastro(LocalDateTime.now());
		kitLivro.setAtivo(true);

		kitLivroRepository.save(kitLivro);
	}

	public void atualizarInformacoes(KitLivro kitLivro, DadosAtualizacaoKitLivro dados) {
		if (dados.nome() != null &&
				!Objects.equals(kitLivro.getNome(), dados.nome()) &&
				kitLivroRepository.existsByNome(dados.nome())) {
			throw new ValidacaoException(
					"Não foi possível atualizar o kit livro. O nome informado já está cadastrado"
			);
		}

		if (dados.nome() != null) {
			kitLivro.setNome(dados.nome());
		}

		if (dados.descricao() != null) {
			kitLivro.setDescricao(dados.descricao());
		}

		if (dados.valor() != null) {
			if (dados.valor() < 0) {
				throw new ValidacaoException(
						"Não foi possível atualizar o kit livro. O valor tem que ser maior que zero."
				);
			}

			kitLivro.setValor(dados.valor());
		}

		if (dados.quantidadeDisponivel() != null) {
			if (dados.quantidadeDisponivel() < 0) {
				throw new ValidacaoException(
						"Não foi possível atualizar o kit livro. A quantidade disponível tem que ser maior que zero."
				);
			}

			kitLivro.setQuantidadeDisponivel(dados.quantidadeDisponivel());
		}

		kitLivro.setDataAtualizacao(LocalDateTime.now());
	}

	public void deletar(KitLivro kitLivro) {
		if (kitLivro.getListaPendenciaKitLivro().stream().anyMatch(ListaPendencia -> SituacaoListaPendenciaEnum.PENDENTE.equals(ListaPendencia.getListaPendencia().getSituacao()))) {
			throw new ValidacaoException(
					"Não foi possível excluir o kit livro. O mesmo está em uma lista de pendência que está pendente."
			);
		}

		kitLivro.setAtivo(false);
		kitLivro.setDataAtualizacao(LocalDateTime.now());
	}
}
