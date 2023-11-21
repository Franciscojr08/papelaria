package listaPendencia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListaPendenciaService {
    @Autowired
    private ListaPendenciaRepository repository;
    public void cadastrar(DadosCadastroListaPendencia pendencia){
        var pendLivro = pendencia.livros();
        var lista = repository.getListaPendenciaById(pendencia.idPedido());
//        DadosCadastroListaPendencia(dados,pedido);
//        cadastrarPedidoKitLivro(dados,pedido);
    }
}
