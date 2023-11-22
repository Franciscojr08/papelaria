package papelaria.ideal.api.kitLivro;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KitLivroRepository extends JpaRepository<KitLivro,Long> {

	boolean existsByIdAndAtivoFalse(Long id);
}
