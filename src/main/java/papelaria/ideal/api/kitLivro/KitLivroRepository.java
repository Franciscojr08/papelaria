package papelaria.ideal.api.kitLivro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KitLivroRepository extends JpaRepository<KitLivro,Long> {


	Boolean existsByAtivoTrueAndNome(String nome);

	Page<KitLivro> findAllByAtivoTrue(Pageable page);

	Boolean existsByIdAndAtivoTrue(Long id);
}
