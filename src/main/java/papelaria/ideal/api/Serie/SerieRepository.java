package papelaria.ideal.api.Serie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie,Long> {

	Boolean existsByNomeAndAtivoTrue(String nome);

	Boolean existsByIdAndAtivoTrue(Long id);

	Page<Serie> findAllByAtivoTrue(Pageable page);
}
