package papelaria.ideal.api.livro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import papelaria.ideal.api.livro.livromodel.Livro;


import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findByAtivoTrue();

    // Aqui você pode adicionar outros métodos de consulta, se necessário
}

