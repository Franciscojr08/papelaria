package papelaria.ideal.api.kitLivro;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface KitLivroRepository extends JpaRepository<KitLivro,Long> {

	boolean existsByIdAndAtivoFalse(Long id);
}
