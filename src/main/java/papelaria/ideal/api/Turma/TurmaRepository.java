package papelaria.ideal.api.Turma;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TurmaRepository extends JpaRepository<Turma,Long> {

	Page<Turma> findAllByAtivoTrue(Pageable page);

	Boolean existsByNomeAndSerieIdAndAtivoTrue(String nome, Long serieId);

	Boolean existsByNomeAndAtivoTrue(String nome);

	Boolean existsByIdAndAtivoTrue(Long id);

	@Query("SELECT t FROM turma t INNER JOIN t.serie ts WHERE ts.id = :serieId and t.ativo = true")
	Page<Turma> findBySerieId(Long serieId, Pageable pageable);

	List<Turma> findAllByAtivoTrue();
}
