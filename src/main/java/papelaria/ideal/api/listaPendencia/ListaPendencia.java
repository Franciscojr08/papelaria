package papelaria.ideal.api.listaPendencia;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListaPendencia {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataEntrega;
    private SituacaoListaPendenciaEnum situacao;
    private boolean entregue;
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "Produto_id")
//    @Embedded
    @Transient
    private List<DadosCadastroProduto> produto;

    public ListaPendencia(DadosCadastroListaPendencia dados){
        this.dataEntrega = dados.dataEntrega();
        this.dataCadastro = dados.dataCadastro();
        this.situacao = dados.situacao();
    }

    public ListaPendencia(Long aLong, LocalDateTime localDateTime, LocalDateTime localDateTime1, SituacaoListaPendenciaEnum situacao, boolean entregue, List<DadosCadastroPendenciaLivroKitLivro> livros) {
    }


    public void atualizarInformacoes(DadosAtualizacaoListaPendencia dados) {
        if (dados.situacao() != null) {
            this.situacao = SituacaoListaPendenciaEnum.valueOf(String.valueOf(dados.situacao()));
        }
        if (dados.dataEntrega() != null) {
            this.dataEntrega = dados.dataEntrega();
        }
        if (dados.livros() != null) {
            this.produto.addAll(dados.livros());
        }
        if (dados.kitLivros() != null) {
            this.produto.addAll(dados.kitLivros());
        }
        if (dados.entregue()) {
            this.entregue = true;
            this.situacao = SituacaoListaPendenciaEnum.ENTREGUE;
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

