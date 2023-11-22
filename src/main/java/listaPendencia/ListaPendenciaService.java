package listaPendencia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListaPendenciaService {
    @Autowired
    private ListaPendenciaRepository repository;
    private void cadastrarListaLivro(DadosCadastroListaPendencia dados) {
        if (dados.livros().isEmpty()) {
            return;
        }
        var lista = new ListaPendencia(
                dados.idPedido(),
                dados.dataCadastro(),
                dados.dataEntrega(),
                dados.situacao(),
                dados.entregue(),
                dados.livros()
        );

        repository.save(lista);
    }
    private void cadastrarListaKitLivro(DadosCadastroListaPendencia dados) {
        if (dados.kitLivros().isEmpty()) {
            return;
        }
        var lista = new ListaPendencia(
                dados.idPedido(),
                dados.dataCadastro(),
                dados.dataEntrega(),
                dados.situacao(),
                dados.entregue(),
                dados.kitLivros()
        );

        repository.save(lista);
    }

//    private void cadastrarPedidoLivro(DadosCadastroListaPendencia dados, Pedido pedido) {
//        if (dados.livros().isEmpty()) {
//            return;
//        }
//
//        for (DadosCadastroPendenciaLivroKitLivro dadosLivro : dados.livros()) {
//            var livro = livroRepository.getReferenceById(dadosLivro.id());
//
//            if (livro.getQuantidade() < dadosLivro.quantidade()) {
//                var diffQuantidade = dadosLivro.quantidade() - livro.getQuantidade();
//                var pendenciaLivro = new DadosCadastroPedidoPedenciaLivroKitLivro(livro.getId(),diffQuantidade);
//
//                listaPendenciaLivro.add(pendenciaLivro);
//                livroService.atualizarQuantidade(0L);
//            } else {
//                var quantidade = livro.getQuantidade() - dadosLivro.quantidade();
//                livroService.atualizarQuantidade(quantidade);
//            }
//
//            pedidoRepository.savePedidoLivro(dadosLivro.quantidade(), pedido.getId(), livro.getId());
//        }
//    }
//
//    private void CadastroPendenciaLivroKitLivro(DadosCadastroPendenciaLivroKitLivro dados, ListaPendencia lista) {
//        if (dados.kitLivros().isEmpty()) {
//            return;
//        }
//
//        for (DadosCadastroPedidoPedenciaLivroKitLivro dadosKitLivro : dados.kitLivros()) {
//            var kitLivro = kitLivroRepository.getReferenceById(dadosKitLivro.id());
//
//            if (kitLivro.getQuantidade() < dadosKitLivro.quantidade()) {
//                var diffQuantidade = dadosKitLivro.quantidade() - kitLivro.getQuantidade();
//                var pendenciaKitLivro = new DadosCadastroPedidoPedenciaLivroKitLivro(kitLivro.getId(),diffQuantidade);
//
//                listaPendenciaKitLivro.add(pendenciaKitLivro);
//                kitLivroService.atualizarQuantidade(0L);
//            } else {
//                var quantidade = kitLivro.getQuantidade() - dadosKitLivro.quantidade();
//                kitLivroService.atualizarQuantidade(quantidade);
//            }
//
//            pedidoRepository.savePedidoKitLivro(dadosKitLivro.quantidade(), pedido.getId(), kitLivro.getId());
//        }

}
