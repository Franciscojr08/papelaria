package papelaria.ideal.api.listaPendencia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListaPendenciaRepository extends JpaRepository<ListaPendencia, Long> {

    Page<ListaPendencia> findAllByAtivoTrue(Pageable paginacao);

}
