package listaPendencia;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListaPendencia {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Cliente_id")
    private Cliente cliente;
    private int dataCadastro;
    private int dataEntrega;
    private String situacao;
    private boolean entregue;
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "Produto_id")
    @Embedded
    private Produto produto;

    public ListaPendencia(DadosCadastroListaPendencia dados){
        this.cliente = dados.cliente();
        this.produto = dados.produto();
        this.dataEntrega = dados.dataEntrega();
        this.dataCadastro = dados.dataCadastro();
        this.situacao = dados.situacao();
    }
    public void atualizarInformacoes(DadosAtualizacaoListaPendencia dados) {
        if (dados.situacao() != null) {
            this.situacao = dados.situacao();
        }
        if (dados.dataEntrega() != 0) {
            this.dataEntrega = dados.dataEntrega();
        }
        if (dados.entregue()) {
            this.entregue = true;
            this.situacao = "Entregue";
        }

    }
    public void excluir() {
        this.entregue = true;
    }


//    public List<ListaPendencia> listar(){
//
//        ListaPendencia pendencia = new ListaPendencia( id, Cliente cliente, int data_cadastro, int data_entrega, String situacao, Produto produto);
//    }
}

