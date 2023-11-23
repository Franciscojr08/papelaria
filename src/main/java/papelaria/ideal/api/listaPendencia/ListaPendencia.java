package papelaria.ideal.api.listaPendencia;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import papelaria.ideal.api.errors.ValidacaoException;
import papelaria.ideal.api.listaPendencia.listaPendenciaKitLivro.DadosListaPendenciaKitLivro;
import papelaria.ideal.api.listaPendencia.listaPendenciaKitLivro.ListaPendenciaKitLivro;
import papelaria.ideal.api.listaPendencia.listaPendenciaLivro.DadosListaPendenciaLivro;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SituacaoListaPendenciaEnum situacao;

    private LocalDateTime dataCadastro;
    private LocalDateTime dataEntrega;
    private LocalDateTime dataAtualizacao;
    private Boolean entregue;
    private Boolean ativo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @Cascade({CascadeType.ALL})
    @OneToMany(mappedBy = "listaPendencia", fetch = FetchType.LAZY)
    private List<ListaPendenciaKitLivro> listaPendenciaKitLivro = new ArrayList<>();

    @Cascade({CascadeType.ALL})
    @OneToMany(mappedBy = "listaPendencia", fetch = FetchType.LAZY)
    private List<ListaPendenciaLivro> listaPendenciaLivro = new ArrayList<>();

    public ListaPendencia(
            Pedido pedido,
            LocalDateTime dataCadastro,
            LocalDateTime dataEntrega,
            SituacaoListaPendenciaEnum situacao,
            Boolean entregue
    ) {
        this.pedido = pedido;
        this.dataCadastro = dataCadastro;
        this.dataEntrega = dataEntrega;
        this.situacao = situacao;
        this.entregue = entregue;
    }

    public void atualizarInformacoes(DadosAtualizacaoListaPendencia dados) {
        if (dados.situacao() != null) {
            this.situacao = dados.situacao();
        }

        if (dados.dataEntrega() != null) {
            if (dados.dataEntrega().isBefore(this.pedido.getDataPedido())) {
                throw new ValidacaoException("A data de entrega não pode ser inferior a data do pedido.");
            }

            this.dataEntrega = dados.dataEntrega();
        }

        if (dados.entregue()) {
            this.entregue = true;
            this.situacao = SituacaoListaPendenciaEnum.ENTREGUE;
        }

        this.dataAtualizacao = LocalDateTime.now();
    }

    public List<DadosListaPendenciaLivro> getDadosListaPendenciaLivro() {
        if (this.listaPendenciaLivro == null) {
            return new ArrayList<>();
        }

        List<DadosListaPendenciaLivro> listaPendenciaLivroList = new ArrayList<>();

        for (ListaPendenciaLivro listaPendenciaLivro : this.listaPendenciaLivro) {
            var dadosListaPendenciaLivro = new DadosListaPendenciaLivro(
                    listaPendenciaLivro.getLivro().getId(),
                    listaPendenciaLivro.getLivro().getIdentificador(),
                    listaPendenciaLivro.getLivro().getNome(),
                    listaPendenciaLivro.getQuantidade()
            );

            listaPendenciaLivroList.add(dadosListaPendenciaLivro);
        }

        return listaPendenciaLivroList;
    }

    public List<DadosListaPendenciaKitLivro> getDadosListaPendenciaKitLivro() {
        if (this.listaPendenciaKitLivro == null) {
            return new ArrayList<>();
        }

        List<DadosListaPendenciaKitLivro> listaPendenciaKitLivroList = new ArrayList<>();

        for (ListaPendenciaKitLivro listaPendenciaKitLivro : this.listaPendenciaKitLivro) {
            var dadosListaPendenciaKitLivro = new DadosListaPendenciaKitLivro(
                    listaPendenciaKitLivro.getKitLivro().getId(),
                    listaPendenciaKitLivro.getKitLivro().getNome(),
                    listaPendenciaKitLivro.getQuantidade()
            );

            listaPendenciaKitLivroList.add(dadosListaPendenciaKitLivro);
        }

        return listaPendenciaKitLivroList;
    }
}

