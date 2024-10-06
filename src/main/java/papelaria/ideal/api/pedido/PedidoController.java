package papelaria.ideal.api.pedido;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import papelaria.ideal.api.errors.DadosResponse;
import papelaria.ideal.api.errors.ValidacaoException;
import papelaria.ideal.api.pedido.records.*;

import java.time.LocalDateTime;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/pedido")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private PedidoRepository pedidoRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<DadosResponse> cadastrar(@RequestBody @Valid DadosCadastroPedido dados) {
		pedidoService.cadastrar(dados);
		var dadosResponse = new DadosResponse(
				LocalDateTime.now(),
				"Sucesso",
				HttpStatus.OK.value(),
				"Pedido cadastrado com sucesso!"
		);

		return ResponseEntity.ok().body(dadosResponse);
	}

	@GetMapping
	public ResponseEntity<Page<DadosListagemPedido>> listar(Pageable paginacao) {
		var page = pedidoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemPedido::new);

		return ResponseEntity.ok().body(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoPedido> detalhar(@PathVariable Long id) {
		if (!pedidoRepository.existsByIdAndAtivoTrue(id)) {
			throw new ValidacaoException("Pedido não encontrado ou inativo.");
		}

		return ResponseEntity.ok().body(new DadosDetalhamentoPedido(pedidoRepository.getReferenceById(id)));
	}

	@GetMapping("/listar-por-kit/{kitLivroId}")
	public ResponseEntity<Page<DadosListagemPedido>> listarPorKitLivro(
			@PathVariable Long kitLivroId,
			Pageable pageable
	) {
		Page<DadosListagemPedido> pedidos = pedidoService.listarPedidosPorKitLivro(kitLivroId, pageable);
		return ResponseEntity.ok(pedidos);
	}

	@GetMapping("listar-por-livro/{livroId}")
	public ResponseEntity<Page<DadosListagemPedido>> listarPorLivro(
			@PathVariable Long livroId,
			Pageable pageable
	) {
		var page = pedidoRepository.findByLivroIdAndAtivoTrue(livroId,pageable).map(DadosListagemPedido::new);

		return ResponseEntity.ok().body(page);
	}

	@GetMapping("/listar-por-cliente/{clienteId}")
	public ResponseEntity<Page<DadosListagemPedido>> listarPorCliente(
			@PathVariable Long clienteId,
			Pageable pageable
	) {
		var page = pedidoRepository.findAllByAtivoTrueAndClienteId(clienteId, pageable).map(DadosListagemPedido::new);

		return ResponseEntity.ok().body(page);
	}

	@PutMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoPedido> atualizar(@RequestBody @Valid DadosAtualizacaoPedido dados) {
		if (!pedidoRepository.existsByIdAndAtivoTrue(dados.id())) {
			throw new ValidacaoException("Pedido não encontrado ou inativo.");
		}

		pedidoService.atualizarInformacoes(pedidoRepository.getReferenceById(dados.id()),dados);

		return ResponseEntity.ok().body(new DadosDetalhamentoPedido(pedidoRepository.getReferenceById(dados.id())));
	}

	@DeleteMapping
	@Transactional
	public ResponseEntity<DadosResponse> deletar(@RequestBody @Valid DadosCancelamentoPedido dados) {
		if (!pedidoRepository.existsByIdAndAtivoTrue(dados.pedidoId())) {
			throw new ValidacaoException("Pedido não encontrado ou inativo.");
		}

		pedidoService.cancelar(pedidoRepository.getReferenceById(dados.pedidoId()),dados);

		var dadosResponse = new DadosResponse(
				LocalDateTime.now(),
				"Sucesso",
				HttpStatus.OK.value(),
				"Pedido cancelado com sucesso!"
		);

		return ResponseEntity.ok().body(dadosResponse);
	}
}
