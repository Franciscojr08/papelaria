package listaPendencia;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ListaPendenciaRepository extends JpaRepository<ListaPendencia, Long> {
    Page<ListaPendencia> findAllByEntregueFalse(Pageable paginacao);
    Page<ListaPendencia> findById(Pageable paginacao);
    Page<ListaPendencia> findByProdutoMatches(Pageable paginacao);
    void deleteBy(long id);
}
