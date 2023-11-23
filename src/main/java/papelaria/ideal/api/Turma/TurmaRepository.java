package papelaria.ideal.api.Turma;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurmaRepository extends JpaRepository<Turma,Long> {

	Page<Turma> findAllByAtivoTrue(Pageable page);

	Boolean existsByNomeAndSerieId(String nome, Long serieId);

	Boolean existsByNome(String nome);
}
