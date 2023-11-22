package papelaria.ideal.api.listaPendencia;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import papelaria.ideal.api.listaPendencia.listaPendenciaKitLivro.ListaPendenciaKitLivro;
import papelaria.ideal.api.listaPendencia.listaPendenciaLivro.ListaPendenciaLivro;
import papelaria.ideal.api.pedido.Pedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "lista_pendencia")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ListaPendencia {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    private LocalDateTime dataCadastro;
    private LocalDateTime dataEntrega;
    private SituacaoListaPendenciaEnum situacao;
    private boolean entregue;

    @Cascade({CascadeType.ALL})
    @OneToMany(mappedBy = "lista_pendencia", fetch = FetchType.LAZY)
    private List<ListaPendenciaKitLivro> listaPendenciaKitLivro = new ArrayList<>();;

    @Cascade({CascadeType.ALL})
    @OneToMany(mappedBy = "lista_pendencia", fetch = FetchType.LAZY)
    private List<ListaPendenciaLivro> listaPendenciaLivro = new ArrayList<>();;

    public ListaPendencia(DadosCadastroListaPendencia dados){
        this.dataEntrega = dados.dataEntrega();
        this.dataCadastro = dados.dataCadastro();
        this.situacao = dados.situacao();
    }

    public ListaPendencia(Long aLong, LocalDateTime localDateTime, LocalDateTime localDateTime1, SituacaoListaPendenciaEnum situacao, boolean entregue, List<DadosCadastroPendenciaLivroKitLivro> livros) {
    }

    public ListaPendencia(Long aLong, LocalDateTime localDateTime, LocalDateTime localDateTime1, SituacaoListaPendenciaEnum situacao, boolean entregue) {
    }


    public void atualizarInformacoes(DadosAtualizacaoListaPendencia dados) {
        if (dados.situacao() != null) {
            this.situacao = SituacaoListaPendenciaEnum.valueOf(String.valueOf(dados.situacao()));
        }
        if (dados.dataEntrega() != null) {
            this.dataEntrega = dados.dataEntrega();
        }
//        if (dados.livros() != null) {
//            this.pendenciaLivro.addAll(dados.livros());
//        }
//        if (dados.kitLivros() != null) {
//            this.pendenciaKitLivro.addAll(dados.kitLivros());
//        }
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

