package papelaria.ideal.api.livro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro,Long> {

	Boolean existsByIdAndAtivoFalse(Long id);
	Boolean existsByIdAndAtivoTrue(Long id);

	Boolean existsByAtivoTrueAndIdentificador(String identificador);

	Page<Livro> findAllByAtivoTrue(Pageable page);
}
