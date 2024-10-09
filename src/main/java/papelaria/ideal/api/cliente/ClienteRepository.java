package papelaria.ideal.api.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Boolean existsByAtivoTrueAndCpf(String cpf);

    Page<Cliente> findAllByAtivoTrue(Pageable page);

    Boolean existsByIdAndAtivoTrue(Long id);

    List<Cliente> findAllByAtivoTrue();
}