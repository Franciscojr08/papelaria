package papelaria.ideal.api.pedido;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {

	Page<Pedido> findAllByAtivoTrue(Pageable page);

	Boolean existsByIdAndAtivoTrue(Long id);

	@Query("SELECT p FROM pedido p JOIN p.pedidoKitLivro pk WHERE pk.kitLivro.id = :kitLivroId and p.ativo = true ")
	Page<Pedido> findByAtivoTrueAndKitLivroId(Long kitLivroId, Pageable pageable);
}
