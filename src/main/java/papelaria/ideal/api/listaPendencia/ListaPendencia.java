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
import papelaria.ideal.api.listaPendencia.records.DadosAtualizacaoListaPendencia;
import papelaria.ideal.api.pedido.Pedido;
import papelaria.ideal.api.pedido.kitLivro.PedidoKitLivro;
import papelaria.ideal.api.pedido.livro.PedidoLivro;

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
            SituacaoListaPendenciaEnum situacao,
            Boolean ativo
    ) {
        this.pedido = pedido;
        this.dataCadastro = dataCadastro;
        this.situacao = situacao;
        this.ativo = ativo;
    }

    public Boolean todosItensEntregues() {
        var todosLivrosEntregues = this.listaPendenciaLivro.stream().allMatch(ListaPendenciaLivro::todosItensEntregues);
        var todosKitLivrosEntregues = this.listaPendenciaKitLivro.stream().allMatch(ListaPendenciaKitLivro::todosItensEntregues);

	    return todosLivrosEntregues && todosKitLivrosEntregues;
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
                    listaPendenciaLivro.getQuantidade(),
                    listaPendenciaLivro.getQuantidadeEntregue()
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
                    listaPendenciaKitLivro.getQuantidade(),
                    listaPendenciaKitLivro.getQuantidadeEntregue()
            );

            listaPendenciaKitLivroList.add(dadosListaPendenciaKitLivro);
        }

        return listaPendenciaKitLivroList;
    }

    public Boolean isEntregue() {
        return this.situacao == SituacaoListaPendenciaEnum.ENTREGUE;
    }

    public Long getQuantidadeLivrosByEntregueTrueOrFalse(Boolean entregue) {
        if (this.listaPendenciaLivro == null) {
            return 0L;
        }

        var quantidadeLivros = 0L;
        for (ListaPendenciaLivro listaPendenciaLivro : this.listaPendenciaLivro) {
            if (entregue) {
                quantidadeLivros += listaPendenciaLivro.getQuantidadeEntregue();
            } else {
                quantidadeLivros += listaPendenciaLivro.getQuantidade();
            }
        }

        return quantidadeLivros;
    }

    public Long getQuantidadeKitLivrosByEntregueTrueOrFalse(Boolean entregue) {
        if (this.listaPendenciaKitLivro == null) {
            return 0L;
        }

        var quantidadeLivros = 0L;
        for (ListaPendenciaKitLivro listaPendenciaKitLivro : this.listaPendenciaKitLivro) {
            if (entregue) {
                quantidadeLivros += listaPendenciaKitLivro.getQuantidadeEntregue();
            } else {
                quantidadeLivros += listaPendenciaKitLivro.getQuantidade();
            }
        }

        return quantidadeLivros;
    }

    public Boolean algumItemEntregue() {
        var livrosEntregues = this.listaPendenciaLivro.stream().mapToLong(ListaPendenciaLivro::getQuantidadeEntregue).sum();
        var kitLivrosEntregues = this.listaPendenciaKitLivro.stream().mapToLong(ListaPendenciaKitLivro::getQuantidadeEntregue).sum();

        return (livrosEntregues > 0) || (kitLivrosEntregues > 0);
    }
}