package papelaria.ideal.api.kitLivro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import papelaria.ideal.api.kitLivro.model.KitLivro;

import java.util.List;

@Repository
public interface KitLivroRepository extends JpaRepository<KitLivro, Long> {
    List<KitLivro> findByAtivoTrue();
}
