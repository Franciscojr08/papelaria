package papelaria.ideal.api.livro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface LivroRepository extends JpaRepository<Livro,Long> {

	boolean existsByIdAndAtivoFalse(Long id);
}
