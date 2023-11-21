package listaPendencia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ListaPendenciaRepository extends JpaRepository<ListaPendencia, Long> {

    Optional<ListaPendencia> getListaPendenciaById(Long id);
    Page<ListaPendencia> findAllByEntregueFalse(Pageable paginacao);

    Page<ListaPendencia> findById(Pageable paginacao);
    @Modifying
    @Query(value = "INSERT INTO lista_pendencia_livro (quantidade, pedido_id, livro_id) VALUES (:quantidade, :pedidoId, :livroId)", nativeQuery = true)
    void savePedidoLivro(@Param("quantidade") Long quantidade, @Param("pedidoId") Long pedidoId, @Param("livroId") Long livroId);

    @Modifying
    @Query(value = "INSERT INTO lista_pendencia_kitLivro (quantidade, pedido_id, kitlivro_id) VALUES (:quantidade, :pedidoId, :kitlivroId)", nativeQuery = true)
    void savePedidoKitLivro(@Param("quantidade") Long quantidade, @Param("pedidoId") Long pedidoId, @Param("kitlivroId") Long kitlivroId);

    void deleteBy(long id);
}
