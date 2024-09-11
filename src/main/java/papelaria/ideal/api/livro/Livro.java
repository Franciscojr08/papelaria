package papelaria.ideal.api.livro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import papelaria.ideal.api.Serie.Serie;
import papelaria.ideal.api.listaPendencia.records.DadosListagemListaPendencia;
import papelaria.ideal.api.listaPendencia.SituacaoListaPendenciaEnum;
import papelaria.ideal.api.listaPendencia.listaPendenciaLivro.ListaPendenciaLivro;
import papelaria.ideal.api.pedido.records.DadosListagemPedido;
import papelaria.ideal.api.pedido.livro.PedidoLivro;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "livro")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Livro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "livro", fetch = FetchType.LAZY)
	private List<PedidoLivro> pedidoLivro;

	@OneToMany(mappedBy = "livro", fetch = FetchType.LAZY)
	private List<ListaPendenciaLivro> listaPendenciaLivro;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "serie_id")
	private Serie serie;

	private String identificador;
	private String nome;
	private Boolean usoInterno;
	private Float valor;
	private Long quantidadeDisponivel;
	private LocalDateTime dataCadastro;
	private LocalDateTime dataAtualizacao;
	private Boolean ativo;

	public Long getQuantidadePedidosAtivos() {
		if (this.pedidoLivro == null) {
			return 0L;
		}

		Long iTotal = 0L;
		for (PedidoLivro pedidoLivro : this.pedidoLivro) {
			var pedido = pedidoLivro.getPedido();
			if (pedido.getAtivo()) {
				iTotal++;
			}
		}

		return iTotal;
	}

	public Long getQuantidadePendenciasAtivas() {
		if (this.listaPendenciaLivro == null) {
			return 0L;
		}

		var iTotal = 0L;

		for (ListaPendenciaLivro listaPendenciaLivro : this.listaPendenciaLivro) {
			var listaPendencia = listaPendenciaLivro.getListaPendencia();
			if (listaPendencia.getAtivo() && listaPendencia.getSituacao() == SituacaoListaPendenciaEnum.PENDENTE) {
				iTotal++;
			}
		}

		return iTotal;
	}

	public List<DadosListagemPedido> getDadosPedido() {
		if (this.pedidoLivro == null) {
			return new ArrayList<>();
		}

		List<DadosListagemPedido> dadosListagemPedidos = new ArrayList<>();

		for (PedidoLivro pedidoLivro : this.pedidoLivro) {
			var pedido = pedidoLivro.getPedido();
			if (!pedido.getAtivo()) {
				continue;
			}

			dadosListagemPedidos.add(new DadosListagemPedido(pedido));
		}

		return dadosListagemPedidos;
	}

	public List<DadosListagemListaPendencia> getDadosListaPendencia() {
		if (this.listaPendenciaLivro == null) {
			return new ArrayList<>();
		}

		List<DadosListagemListaPendencia> dadosListagemListaPendencias = new ArrayList<>();

		for (ListaPendenciaLivro listaPendenciaLivro : this.listaPendenciaLivro) {
			var listaPendencia = listaPendenciaLivro.getListaPendencia();
			if (!listaPendencia.getAtivo() && listaPendencia.getSituacao() != SituacaoListaPendenciaEnum.PENDENTE){
				continue;
			}

			dadosListagemListaPendencias.add(new DadosListagemListaPendencia(listaPendencia));
		}

		return dadosListagemListaPendencias;
	}

	public String getDescricaoUsoInterno() {
		return this.usoInterno ? "Sim" : "Não";
	}
}
