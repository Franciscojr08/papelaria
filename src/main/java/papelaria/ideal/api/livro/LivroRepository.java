package papelaria.ideal.api.livro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LivroRepository extends JpaRepository<Livro,Long> {

	Boolean existsByIdAndAtivoTrue(Long id);

	Boolean existsByAtivoTrueAndIdentificador(String identificador);

	Page<Livro> findAllByAtivoTrue(Pageable page);

	@Query("SELECT l FROM livro l INNER JOIN l.serie ls WHERE ls.id = :serieId")
	Page<Livro> findBySerieId(Long serieId, Pageable pageable);
}
