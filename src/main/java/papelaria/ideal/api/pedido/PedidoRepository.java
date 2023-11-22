package papelaria.ideal.api.pedido;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {

	public Page<Pedido> findAllByAtivoTrue(Pageable page);

}
