package papelaria.ideal.api.kitLivro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import papelaria.ideal.api.listaPendencia.records.DadosListagemListaPendencia;
import papelaria.ideal.api.listaPendencia.SituacaoListaPendenciaEnum;
import papelaria.ideal.api.listaPendencia.listaPendenciaKitLivro.ListaPendenciaKitLivro;
import papelaria.ideal.api.pedido.records.DadosListagemPedido;
import papelaria.ideal.api.pedido.kitLivro.PedidoKitLivro;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "kit_livro")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class KitLivro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "kitLivro", fetch = FetchType.LAZY)
	private List<PedidoKitLivro> pedidoKitLivro;

	@OneToMany(mappedBy = "kitLivro", fetch = FetchType.LAZY)
	private List<ListaPendenciaKitLivro> listaPendenciaKitLivro;

	private String nome;
	private String descricao;
	private Float valor;
	private Long quantidadeDisponivel;
	private LocalDateTime dataCadastro;
	private LocalDateTime dataAtualizacao;
	private Boolean ativo;

	public Long getQuantidadePedidosAtivos() {
		if (this.pedidoKitLivro == null) {
			return 0L;
		}

		Long iTotal = 0L;
		for (PedidoKitLivro pedidoKitLivro : this.pedidoKitLivro) {
			var kitLivro = pedidoKitLivro.getKitLivro();
			if (kitLivro.getAtivo()) {
				iTotal++;
			}
		}

		return iTotal;
	}

	public Long getQuantidadePendenciasAtivas() {
		if (this.listaPendenciaKitLivro == null) {
			return 0L;
		}

		var iTotal = 0L;

		for (ListaPendenciaKitLivro listaPendenciaKitLivro : this.listaPendenciaKitLivro) {
			var listaPendencia = listaPendenciaKitLivro.getListaPendencia();
			if (listaPendencia.getAtivo() && listaPendencia.getSituacao() == SituacaoListaPendenciaEnum.PENDENTE) {
				iTotal++;
			}
		}

		return iTotal;
	}

	public List<DadosListagemPedido> getDadosPedido() {
		if (this.pedidoKitLivro == null) {
			return new ArrayList<>();
		}

		List<DadosListagemPedido> dadosListagemPedidos = new ArrayList<>();

		for (PedidoKitLivro pedidoKitLivro : this.pedidoKitLivro) {
			var pedido = pedidoKitLivro.getPedido();
			if (!pedido.getAtivo()) {
				continue;
			}

			dadosListagemPedidos.add(new DadosListagemPedido(pedido));
		}

		return dadosListagemPedidos;
	}

	public List<DadosListagemListaPendencia> getDadosListaPendencia() {
		if (this.listaPendenciaKitLivro == null) {
			return new ArrayList<>();
		}

		List<DadosListagemListaPendencia> dadosListagemListaPendencias = new ArrayList<>();

		for (ListaPendenciaKitLivro listaPendenciaKitLivro : this.listaPendenciaKitLivro) {
			var listaPendencia = listaPendenciaKitLivro.getListaPendencia();
			if (!listaPendencia.getAtivo() && listaPendencia.getSituacao() != SituacaoListaPendenciaEnum.PENDENTE){
				continue;
			}

			dadosListagemListaPendencias.add(new DadosListagemListaPendencia(listaPendencia));
		}

		return dadosListagemListaPendencias;
	}
}
