package papelaria.ideal.api.listaPendencia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import papelaria.ideal.api.errors.ValidacaoException;
import papelaria.ideal.api.kitLivro.KitLivroRepository;
import papelaria.ideal.api.listaPendencia.listaPendenciaKitLivro.ListaPendenciaKitLivro;
import papelaria.ideal.api.listaPendencia.listaPendenciaLivro.ListaPendenciaLivro;
import papelaria.ideal.api.livro.LivroRepository;
import papelaria.ideal.api.pedido.PedidoRepository;

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
        if (!pedidoRepository.existsById(dados.pedidoId())) {
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

        if (dados.dataEntrega() != null && dados.dataEntrega().isBefore(dados.dataEntrega())) {
            throw new ValidacaoException("A data de entrega não pode ser inferior a data de cadastro.");
        }
    }

    public void cadastrarListaPendencia(DadosCadastroListaPendencia dados) {
        var pedido = pedidoRepository.getReferenceById(dados.pedidoId());

        var lista = new ListaPendencia(
                pedido,
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

        lista.setAtivo(true);
        lista.setDataCadastro(LocalDateTime.now());

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
                    dadosLivro.quantidade()
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

        for (DadosCadastroPendenciaLivroKitLivro dadosKitLivro : dados.kitLivros()) {
            var kitLivro = kitLivroRepository.getReferenceById(dadosKitLivro.id());

            var listaPendenciaKitLivro = new ListaPendenciaKitLivro(
                    null,
                    listaPendencia,
                    kitLivro,
                    dadosKitLivro.quantidade()
            );

            listaPendenciaKitLivroList.add(listaPendenciaKitLivro);
        }

        return listaPendenciaKitLivroList;
    }
}
