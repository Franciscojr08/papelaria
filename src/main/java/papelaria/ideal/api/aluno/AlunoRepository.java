package papelaria.ideal.api.aluno;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    Boolean existsByCpfAndAtivoTrue(String cpf);

    Boolean existsByRgAndAtivoTrue(String rg);

    Boolean existsByMatriculaAndAtivoTrue(String matricula);

    Page<Aluno> findAllByAtivoTrue(Pageable page);

    Boolean existsByIdAndAtivoTrue(Long id);

    @Query("SELECT a FROM aluno a INNER JOIN a.turma at WHERE at.id = :turmaId and a.ativo = true")
    Page<Aluno> findAllByAtivoTrueAndTurmaId(Long turmaId, Pageable pageable);
}
