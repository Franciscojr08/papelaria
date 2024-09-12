package papelaria.ideal.api.Serie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import papelaria.ideal.api.Turma.Turma;

import java.util.List;

public interface SerieRepository extends JpaRepository<Serie,Long> {

	Boolean existsByNomeAndAtivoTrue(String nome);

	Boolean existsByIdAndAtivoTrue(Long id);

	Page<Serie> findAllByAtivoTrue(Pageable page);

	Page<Serie> findAllByAtivoTrueAndNomeContaining(Pageable page, String nome);

	List<Serie> findAllByAtivoTrue();
}
