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
	public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPedido dados) {
		pedidoService.cadastrar(dados);

		return ResponseEntity.ok().body("Pedido cadastrado com sucesso!");
	}

	@GetMapping
	public ResponseEntity<Page<DadosListagemPedido>> listar(Pageable paginacao) {
		var page = pedidoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemPedido::new);

		return ResponseEntity.ok().body(page);
	}
}
