package papelaria.ideal.api.listaPendencia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ListaPendenciaRepository extends JpaRepository<ListaPendencia, Long> {

    Page<ListaPendencia> findAllByAtivoTrue(Pageable paginacao);

    Boolean existsByIdAndAtivoTrue(Long id);

    @Query("SELECT l FROM lista_pendencia l JOIN l.listaPendenciaKitLivro lk WHERE lk.kitLivro.id = :kitLivroId and l.ativo = true")
    Page<ListaPendencia> findByKitLivroIdAndAtivoTrue(Long kitLivroId, Pageable pageable);

    @Query("SELECT l FROM lista_pendencia  l JOIN l.listaPendenciaLivro ll WHERE ll.livro.id = :livroId and l.ativo = true")
    Page<ListaPendencia> findByLivroIdAndAtivoTrue(Long livroId, Pageable pageable);
}
