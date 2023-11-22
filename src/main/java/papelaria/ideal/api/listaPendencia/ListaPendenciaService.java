package papelaria.ideal.api.listaPendencia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import papelaria.ideal.api.kitLivro.KitLivroRepository;
import papelaria.ideal.api.listaPendencia.listaPendenciaKitLivro.ListaPendenciaKitLivro;
import papelaria.ideal.api.listaPendencia.listaPendenciaLivro.ListaPendenciaLivro;
import papelaria.ideal.api.livro.LivroRepository;
import papelaria.ideal.api.pedido.DadosCadastroPedidoLivroKitLivro;

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

    public void cadastrar(DadosCadastroListaPendencia dados){

    }
    public void cadastrarListaPendencia(DadosCadastroListaPendencia dados) {
        if (dados.livros().isEmpty()) {
            return;
        }
        var lista = new ListaPendencia(
                dados.idPedido(),
                dados.dataCadastro(),
                dados.dataEntrega(),
                dados.situacao(),
                dados.entregue()
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
    public void cadastrarListaKitLivro(DadosCadastroListaPendencia dados) {
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

        listaPendenciaRepository.save(lista);
    }
    private List<ListaPendenciaLivro> getListaPendenciaLivro(DadosCadastroListaPendencia dados, ListaPendencia listaPendencia) {
        if (dados.livros() == null) {
            return new ArrayList<>();
        }
        List<ListaPendenciaLivro> listaPendenciaLivroList = new ArrayList<>();
        for (DadosCadastroPendenciaLivroKitLivro dadosLivro : dados.livros()) {
            var livro = livroRepository.getReferenceById(dadosLivro.id());

            var listaPendenciaLivro = new ListaPendenciaLivro(
                    null,
                    listaPendencia,
                    livro,
                    dadosLivro.quantidadeSolicitada()
            );
            listaPendenciaLivroList.add(listaPendenciaLivro);
        }

        return listaPendenciaLivroList;
    }
    private List<ListaPendenciaKitLivro> getListaPendenciaKitLivro(DadosCadastroListaPendencia dados, ListaPendencia listaPendencia) {
        if (dados.kitLivros() == null) {
            return new ArrayList<>();
        }
        List<ListaPendenciaKitLivro> listaPendenciaKitLivroList = new ArrayList<>();
        for (DadosCadastroPendenciaLivroKitLivro dadosKitLivro : dados.livros()) {
            var kitLivro = kitLivroRepository.getReferenceById(dadosKitLivro.id());

            var listaPendenciaKitLivro = new ListaPendenciaKitLivro(
                    null,
                    listaPendencia,
                    kitLivro,
                    dadosKitLivro.quantidadeSolicitada()
            );
            listaPendenciaKitLivroList.add(listaPendenciaKitLivro);
        }

        return listaPendenciaKitLivroList;
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
