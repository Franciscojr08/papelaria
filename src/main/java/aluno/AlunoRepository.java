package aluno;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Optional<Aluno> findByMatricula(String matricula);
    Page<Aluno> findByAtivoTrue(); // alunos ativo
    Page<Aluno> findByCliente_Id(Long clienteId);

    <T> Optional<T> findAllByAtivoTrue(Pageable paginacao);
}
