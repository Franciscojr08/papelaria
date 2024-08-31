package papelaria.ideal.api.kitLivro;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import papelaria.ideal.api.errors.ValidacaoException;
import papelaria.ideal.api.kitLivro.records.DadosAtualizacaoKitLivro;
import papelaria.ideal.api.kitLivro.records.DadosCadastroKitLivro;
import papelaria.ideal.api.kitLivro.records.DadosFiltragemKitLivro;
import papelaria.ideal.api.pedido.SituacaoPedidoEnum;
import papelaria.ideal.api.pedido.kitLivro.PedidoKitLivro;
import papelaria.ideal.api.utils.LivroKitLivroServiceInterface;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class KitLivroService implements LivroKitLivroServiceInterface {

	@Autowired
	private KitLivroRepository kitLivroRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void atualizarQuantidade(Long id, Long quantidade) {
		var kitLivro = kitLivroRepository.getReferenceById(id);
		kitLivro.setQuantidadeDisponivel(quantidade);
	}

	public void cadastrar(DadosCadastroKitLivro dados) {
		if (kitLivroRepository.existsByAtivoTrueAndNome(dados.nome())) {
			throw new ValidacaoException(
					"Não foi possível cadastrar o kit livro. O nome informado já está cadastrado"
			);
		}

		if (dados.quantidadeDisponivel() < 0) {
			throw new ValidacaoException(
					"Não foi possível cadastrar o kit livro. A quantidade disponível deve ser maior que zero."
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
				kitLivroRepository.existsByAtivoTrueAndNome(dados.nome())) {
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
						"Não foi possível atualizar o kit livro. A quantidade disponível deve ser maior que zero."
				);
			}

			kitLivro.setQuantidadeDisponivel(dados.quantidadeDisponivel());
		}

		kitLivro.setDataAtualizacao(LocalDateTime.now());
	}

	public void deletar(KitLivro kitLivro) {
		if (kitLivro.getQuantidadePendenciasAtivas() > 0) {
			throw new ValidacaoException(
					"Não foi possível deletar o kit de livro, o mesmo possui pendências ativas."
			);
		}

		if (kitLivro.getPedidoKitLivro() != null) {
			for (PedidoKitLivro pedidoKitLivro : kitLivro.getPedidoKitLivro()) {
				var pedido = pedidoKitLivro.getPedido();
				if (pedido.getSituacaoPedido() == SituacaoPedidoEnum.PENDENTE) {
					throw new ValidacaoException(
							"Não foi possível deletar o kit de livro, o mesmo possui pedidos pendentes."
					);
				}
			}
		}

		kitLivro.setAtivo(false);
		kitLivro.setDataAtualizacao(LocalDateTime.now());
	}

	public Page<KitLivro> filtrar(DadosFiltragemKitLivro dadosFiltros, Pageable pageable) {
		var kitLivroQueryNative = new KitLivroQueryNative(entityManager);
		return kitLivroQueryNative.filtrarKits(dadosFiltros, pageable);
	}
}
