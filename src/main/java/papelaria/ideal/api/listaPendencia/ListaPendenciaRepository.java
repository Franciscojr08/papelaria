package papelaria.ideal.api.listaPendencia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ListaPendenciaRepository extends JpaRepository<ListaPendencia, Long> {

    Page<ListaPendencia> findAllByAtivoTrue(Pageable paginacao);

    Boolean existsByIdAndAtivoTrue(Long id);

    @Query("SELECT l from lista_pendencia l JOIN l.listaPendenciaKitLivro lk WHERE lk.kitLivro.id = :kitLivroId")
    Page<ListaPendencia> findByKitLivroId(Long kitLivroId, Pageable pageable);
}
