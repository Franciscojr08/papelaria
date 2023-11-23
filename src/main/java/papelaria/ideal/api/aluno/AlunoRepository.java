package papelaria.ideal.api.aluno;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    Boolean existsByCpf(String cpf);

    Boolean existsByRg(String rg);

    Boolean existsByMatricula(String matricula);

    public Page<Aluno> findAllByAtivoTrue(Pageable page);
}
