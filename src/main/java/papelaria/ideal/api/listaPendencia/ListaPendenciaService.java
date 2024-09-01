package papelaria.ideal.api.listaPendencia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import papelaria.ideal.api.errors.ValidacaoException;
import papelaria.ideal.api.kitLivro.KitLivroRepository;
import papelaria.ideal.api.listaPendencia.listaPendenciaKitLivro.ListaPendenciaKitLivro;
import papelaria.ideal.api.listaPendencia.listaPendenciaLivro.ListaPendenciaLivro;
import papelaria.ideal.api.listaPendencia.records.*;
import papelaria.ideal.api.livro.LivroRepository;
import papelaria.ideal.api.pedido.PedidoRepository;
import papelaria.ideal.api.pedido.PedidoService;
import papelaria.ideal.api.pedido.SituacaoPedidoEnum;
import papelaria.ideal.api.pedido.kitLivro.PedidoKitLivro;
import papelaria.ideal.api.pedido.livro.PedidoLivro;
import papelaria.ideal.api.pedido.records.DadosListagemPedido;
import papelaria.ideal.api.pedido.records.DadosPedidoLivroKitLivro;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ListaPendenciaService {

    @Autowired
    private ListaPendenciaRepository listaPendenciaRepository;
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private KitLivroRepository kitLivroRepository;
    @Autowired
    private PedidoRepository pedidoRepository;

    public void cadastrar(DadosCadastroListaPendencia dados) {
        validarIntegridade(dados);
        cadastrarListaPendencia(dados);
    }

    private void validarIntegridade(DadosCadastroListaPendencia dados) {
        if (!pedidoRepository.existsByIdAndAtivoTrue(dados.pedidoId())) {
            throw new ValidacaoException("O pedido informado é inválido ou não está cadastrado.");
        }

        if (dados.livros() == null && dados.kitLivros() == null) {
            throw new ValidacaoException(
                    "Não é possível cadastrar uma lista de pendência sem livro ou kit de livro. " +
                            "Adicione ao menos um livro ou kit de livro à lista de pendências."
            );
        }

        if (dados.dataCadastro().isBefore(pedidoRepository.getReferenceById(dados.pedidoId()).getDataPedido())) {
            throw new ValidacaoException("A data de cadastro não pode ser inferior a data do pedido.");
        }
    }

    public void cadastrarListaPendencia(DadosCadastroListaPendencia dados) {
        var pedido = pedidoRepository.getReferenceById(dados.pedidoId());

        var lista = new ListaPendencia(
                pedido,
                dados.dataCadastro(),
                dados.situacao(),
                true
        );

        var pendenciaLivro = getListaPendenciaLivro(dados, lista);
        var pendenciaKitLivro = getListaPendenciaKitLivro(dados, lista);

        if (!pendenciaLivro.isEmpty()) {
            lista.setListaPendenciaLivro(pendenciaLivro);
        }

        if (!pendenciaKitLivro.isEmpty()) {
            lista.setListaPendenciaKitLivro(pendenciaKitLivro);
        }

        listaPendenciaRepository.save(lista);
    }

    private List<ListaPendenciaLivro> getListaPendenciaLivro(
            DadosCadastroListaPendencia dados,
            ListaPendencia listaPendencia
    ) {
        if (dados.livros() == null) {
            return new ArrayList<>();
        }

        List<ListaPendenciaLivro> listaPendenciaLivroList = new ArrayList<>();

        for (DadosCadastroPendenciaLivroKitLivro dadosLivro : dados.livros()) {
            var livro = livroRepository.getReferenceById(dadosLivro.id());
            listaPendenciaLivroList.add(new ListaPendenciaLivro(
                    null,
                    listaPendencia,
                    livro,
                    dadosLivro.quantidade(),
                    0L
            ));
        }

        return listaPendenciaLivroList;
    }

    private List<ListaPendenciaKitLivro> getListaPendenciaKitLivro(
            DadosCadastroListaPendencia dados,
            ListaPendencia listaPendencia
    ) {
        if (dados.kitLivros() == null) {
            return new ArrayList<>();
        }

        List<ListaPendenciaKitLivro> listaPendenciaKitLivroList = new ArrayList<>();

        for (DadosCadastroPendenciaLivroKitLivro dadosKitLivro : dados.kitLivros()) {
            var kitLivro = kitLivroRepository.getReferenceById(dadosKitLivro.id());
            listaPendenciaKitLivroList.add(new ListaPendenciaKitLivro(
                    null,
                    listaPendencia,
                    kitLivro,
                    dadosKitLivro.quantidade(),
                    0L
            ));
        }

        return listaPendenciaKitLivroList;
    }

    public void atualizar(ListaPendencia listaPendencia, DadosAtualizacaoListaPendencia dados) {
        if (dados.livros() == null && dados.kitLivros() == null) {
            throw new ValidacaoException(
                    "Não foi possível atualizar a lista de pendência. É necessário informar uma quantidade de itens" +
                    "(livro ou kit de livros) que foram entregues nesta atualização."
            );
        }

        if (dados.livros() != null) {
            for (DadosPedidoLivroKitLivro pedidoLivroKitLivro : dados.livros()) {
                for (ListaPendenciaLivro listaPendenciaLivro : listaPendencia.getListaPendenciaLivro()) {
	                if (listaPendenciaLivro.getLivro().getId() != pedidoLivroKitLivro.id()) {
						continue;
	                }

                    atualizarListaPendenciaLivro(pedidoLivroKitLivro,listaPendenciaLivro);
                }

                for (PedidoLivro pedidoLivro : listaPendencia.getPedido().getPedidoLivro()) {
	                if (pedidoLivro.getLivro().getId() != pedidoLivroKitLivro.id()) {
                        continue;
	                }

	                listaPendencia.getPedido().atualizarPedidoLivro(pedidoLivroKitLivro,pedidoLivro);
                }
            }
        }

        if (dados.kitLivros() != null) {
            for (DadosPedidoLivroKitLivro pedidoLivroKitLivro : dados.kitLivros()) {
                for (ListaPendenciaKitLivro listaPendenciaKitLivro : listaPendencia.getListaPendenciaKitLivro()) {
                    if (listaPendenciaKitLivro.getKitLivro().getId() != pedidoLivroKitLivro.id()) {
                        continue;
                    }

					atualizarListaPendenciaKitLivro(pedidoLivroKitLivro,listaPendenciaKitLivro);
                }

                for (PedidoKitLivro pedidoKitLivro : listaPendencia.getPedido().getPedidoKitLivro()) {
                    if (pedidoKitLivro.getKitLivro().getId() != pedidoLivroKitLivro.id()) {
                        continue;
                    }

					listaPendencia.getPedido().atualizarPedidoKitLivro(pedidoLivroKitLivro,pedidoKitLivro);
                }
            }
        }

		if ((listaPendencia.todosItensEntregues() &&
			!listaPendencia.getPedido().todosItensEntregues()) ||
			(!listaPendencia.todosItensEntregues() &&
			listaPendencia.getPedido().todosItensEntregues())
		) {
			throw new ValidacaoException("Ocorreu um erro ao atualizar a lista de pendência e o pedido.");
		}

        if (listaPendencia.todosItensEntregues() &&
            listaPendencia.getPedido().todosItensEntregues()
        ) {
            listaPendencia.setSituacao(SituacaoListaPendenciaEnum.ENTREGUE);
            listaPendencia.setDataEntrega(LocalDateTime.now());
            listaPendencia.getPedido().setSituacaoPedido(SituacaoPedidoEnum.FINALIZADO);
            listaPendencia.getPedido().setDataEntrega(LocalDateTime.now());
        }

        listaPendencia.setDataAtualizacao(LocalDateTime.now());
    }

	private void atualizarListaPendenciaLivro(
			DadosPedidoLivroKitLivro pedidoLivroKitLivro,
			ListaPendenciaLivro listaPendenciaLivro
	) {
		var quantidadeEntregue = listaPendenciaLivro.getQuantidadeEntregue();
		var quantidadePendente = listaPendenciaLivro.getQuantidade() - quantidadeEntregue;

		if (pedidoLivroKitLivro.quantidade() > quantidadePendente) {
			throw new ValidacaoException(
					"Não foi possível atualizar a lista de pendência. A quantidade de livros" +
					" informada nesta atualização é maior do que a quantidade pendente."
			);
		}

		listaPendenciaLivro.setQuantidadeEntregue(quantidadeEntregue + pedidoLivroKitLivro.quantidade());
	}

	private void atualizarListaPendenciaKitLivro(
			DadosPedidoLivroKitLivro pedidoLivroKitLivro,
			ListaPendenciaKitLivro listaPendenciaKitLivro
	) {
		var quantidadeEntregue = listaPendenciaKitLivro.getQuantidadeEntregue();
		var quantidadePendente = listaPendenciaKitLivro.getQuantidade() - quantidadeEntregue;

		if (pedidoLivroKitLivro.quantidade() > quantidadePendente) {
			throw new ValidacaoException(
					"Não foi possível atualizar a lista de pendência. A quantidade de kit de livros" +
					" informada nesta atualização é maior do que a quantidade pendente."
			);
		}

		listaPendenciaKitLivro.setQuantidadeEntregue(quantidadeEntregue + pedidoLivroKitLivro.quantidade());
	}

	public void cancelar(ListaPendencia listaPendencia, DadosCancelamentoListaPendencia dados) {
		if (listaPendencia.isEntregue() && listaPendencia.getPedido().isEntregue()) {
			throw new ValidacaoException(
					"Não é possível cancelar uma lista de pendência entregue de um pedido entregue."
			);
		}

		var atualizarEstoque = dados.atualizarEstoque();
		var pedido = listaPendencia.getPedido();

		if (pedido.todosItensEstaoNaListaPendencia() && !pedido.algumItemEntregue()) {
			listaPendencia.setSituacao(SituacaoListaPendenciaEnum.CANCELADA);
			listaPendencia.setAtivo(false);
			pedido.setSituacaoPedido(SituacaoPedidoEnum.CANCELADO);
			pedido.setAtivo(false);
			return;
		}

		if (listaPendencia.algumItemEntregue() && atualizarEstoque) {
			for (ListaPendenciaLivro listaPendenciaLivro : listaPendencia.getListaPendenciaLivro()) {
				var livro = listaPendenciaLivro.getLivro();
				var quantidadeAtual = livro.getQuantidadeDisponivel();
				livro.setQuantidadeDisponivel(quantidadeAtual + listaPendenciaLivro.getQuantidadeEntregue());
				pedido.removerQuantidadeDeLivroEntregue(livro,listaPendenciaLivro.getQuantidadeEntregue());
			}

			for (ListaPendenciaKitLivro listaPendenciaKitLivro : listaPendencia.getListaPendenciaKitLivro()) {
				var kitLivro = listaPendenciaKitLivro.getKitLivro();
				var quantidadeAtual = kitLivro.getQuantidadeDisponivel();
				kitLivro.setQuantidadeDisponivel(quantidadeAtual + listaPendenciaKitLivro.getQuantidadeEntregue());
				pedido.removerQuantidadeDeKitLivroEntregue(kitLivro,listaPendenciaKitLivro.getQuantidadeEntregue());
			}
		}

		listaPendencia.setSituacao(SituacaoListaPendenciaEnum.CANCELADA);
		listaPendencia.setAtivo(false);

		if (pedido.todosItensEstaoNaListaPendencia()) {
			pedido.setSituacaoPedido(SituacaoPedidoEnum.CANCELADO);
			pedido.setAtivo(false);
		} else {
			pedido.setSituacaoPedido(SituacaoPedidoEnum.FINALIZADO);
			pedido.setDataEntrega(LocalDateTime.now());
		}
	}

	public Page<DadosListagemListaPendencia> listarPedidosPorKitLivro(Long kitLivroId, Pageable pageable) {
		return listaPendenciaRepository.findByKitLivroId(kitLivroId, pageable)
				.map(DadosListagemListaPendencia::new);
	}
}
