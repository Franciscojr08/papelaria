package papelaria.ideal.api.aluno;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    Boolean existsByCpfAndAtivoTrue(String cpf);

    Boolean existsByRgAndAtivoTrue(String rg);

    Boolean existsByMatriculaAndAtivoTrue(String matricula);

    Page<Aluno> findAllByAtivoTrue(Pageable page);

    Boolean existsByIdAndAtivoTrue(Long id);
}
