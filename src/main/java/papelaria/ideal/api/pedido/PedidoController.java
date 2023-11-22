package papelaria.ideal.api.pedido;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private PedidoRepository pedidoRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<String> cadastrar(@RequestBody @Valid DadosCadastroPedido dados) {
		pedidoService.cadastrar(dados);

		return ResponseEntity.ok().body("Pedido cadastrado com sucesso!");
	}

	@GetMapping
	public ResponseEntity<Page<DadosListagemPedido>> listar(Pageable paginacao) {
		var page = pedidoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemPedido::new);

		return ResponseEntity.ok().body(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DadosPedido> detalhar(@PathVariable Long id) {
		var pedido = pedidoRepository.getReferenceById(id);

		return ResponseEntity.ok().body(new DadosPedido(pedido));
	}

	@PutMapping
	@Transactional
	public ResponseEntity<DadosPedido> atualizar(@RequestBody @Valid DadosAtualizacaoPedido dados) {
		var pedido = pedidoRepository.getReferenceById(dados.id());
		pedido.atualizarInformacoes(dados);

		return ResponseEntity.ok().body(new DadosPedido(pedido));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity deletar(@PathVariable Long id) {
		var pedido = pedidoRepository.getReferenceById(id);
		pedido.setAtivo(false);
		pedido.setSituacaoPedido(SituacaoPedidoEnum.CANCELADO);

		return ResponseEntity.noContent().build();
	}
}
