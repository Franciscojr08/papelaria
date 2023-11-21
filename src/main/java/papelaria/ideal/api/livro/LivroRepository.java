package papelaria.ideal.api.livro;

import org.springframework.data.jpa.repository.JpaRepository;
public interface LivroRepository extends JpaRepository<Livro,Long> {

	boolean existsByIdAndAtivoFalse(Long id);
}
