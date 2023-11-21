package papelaria.ideal.api.pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {

	@Modifying
	@Query(value = "INSERT INTO pedido_livro (quantidade, pedido_id, livro_id) VALUES (:quantidade, :pedidoId, :livroId)", nativeQuery = true)
	void savePedidoLivro(@Param("quantidade") Long quantidade, @Param("pedidoId") Long pedidoId, @Param("livroId") Long livroId);

	@Modifying
	@Query(value = "INSERT INTO pedido_kit_livro (quantidade, pedido_id, kitlivro_id) VALUES (:quantidade, :pedidoId, :kitlivroId)", nativeQuery = true)
	void savePedidoKitLivro(@Param("quantidade") Long quantidade, @Param("pedidoId") Long pedidoId, @Param("kitlivroId") Long kitlivroId);
}
