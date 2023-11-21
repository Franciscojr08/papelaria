package listaPendencia;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Getter
@Setter

public class Produto {
 private int dataCadastro;
 private int pedidoId;

 private SituacaoPedidoEnum situacao;
 private boolean entregue;
 private List<Map<String, Integer>> livro = new ArrayList<>();
 private List<Map<String, Integer>> kitLivro = new ArrayList<>();
}
